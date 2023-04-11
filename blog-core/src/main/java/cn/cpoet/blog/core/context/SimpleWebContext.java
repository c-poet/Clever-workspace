package cn.cpoet.blog.core.context;

import cn.cpoet.blog.api.context.WebContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

/**
 * 请求上下文
 *
 * @author CPoet
 */
@RequiredArgsConstructor
public class SimpleWebContext implements WebContext {

    /**
     * 请求信息
     */
    private final ServerWebExchange exchange;

    @Override
    public ServerHttpRequest getRequest() {
        return exchange.getRequest();
    }

    @Override
    public ServerHttpResponse getResponse() {
        return exchange.getResponse();
    }

    @Override
    public <T> T getAttr(String name) {
        return exchange.getAttribute(name);
    }

    @Override
    public void setAttr(String name, Object value) {
        exchange.getAttributes().put(name, value);
    }

    @Override
    public List<String> getHeaders(String name) {
        return exchange.getRequest().getHeaders().get(name);
    }

    @Override
    public String getHeader(String name) {
        List<String> headers = getHeaders(name);
        return CollectionUtils.isEmpty(headers) ? null : headers.get(0);
    }
}
