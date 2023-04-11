package cn.cpoet.blog.auth.resolver;

import cn.cpoet.blog.api.context.AppContextHolder;
import cn.cpoet.blog.api.context.Subject;
import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证主体方法参数解析器
 *
 * @author wanggf
 */
public class SubjectArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return Subject.class == methodParameter.getParameterType();
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        return Mono.fromSupplier(AppContextHolder.getAuthContext()::curSubject);
    }
}
