package cn.cpoet.blog.core.support;

import cn.cpoet.blog.core.vo.RetVO;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author CPoet
 */
public class CustomResponseBodyHandler extends ResponseBodyResultHandler {

    private final static MethodParameter METHOD_PARAMETER;

    static {
        try {
            Method method = CustomResponseBodyHandler.class.getDeclaredMethod("genNullBody");
            METHOD_PARAMETER = new MethodParameter(method, -1);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("初始化统一响应体信息失败", e);
        }
    }

    public CustomResponseBodyHandler(List<HttpMessageWriter<?>> writers,
                                     RequestedContentTypeResolver resolver,
                                     ReactiveAdapterRegistry registry) {
        super(writers, resolver, registry);
    }

    @Override
    public boolean supports(HandlerResult result) {
        return super.supports(result);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    protected Mono<Void> writeBody(Object body, MethodParameter bodyParameter, ServerWebExchange exchange) {
        Object newBody;
        if (body instanceof Mono) {
            newBody = ((Mono) body)
                .map(this::handleBody)
                .switchIfEmpty(genNullBody());
        } else if (body instanceof Flux) {
            newBody = ((Flux) body)
                .collectList()
                .map(this::handleBody);
        } else {
            newBody = handleBody(body);
        }
        return super.writeBody(newBody, METHOD_PARAMETER, exchange);
    }

    /**
     * 生成统一响应体，空数据
     * <p>将会使用该方法生成{@link MethodParameter}</p>
     *
     * @return 响应结果
     */
    private Mono<Object> genNullBody() {
        return Mono.just(handleBody(null));
    }

    /**
     * 包装响应体
     *
     * @param body 响应内容
     * @return 统一响应体
     */
    @SuppressWarnings("rawtypes")
    private Object handleBody(Object body) {
        RetVO retVO;
        if (body instanceof RetVO) {
            retVO = (RetVO) body;
        } else {
            retVO = RetVO.ok(body);
        }
        retVO.setTimestamp(System.currentTimeMillis());
        return retVO;
    }
}
