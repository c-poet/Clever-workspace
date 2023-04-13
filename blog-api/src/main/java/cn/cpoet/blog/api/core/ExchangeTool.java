package cn.cpoet.blog.api.core;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

import java.util.List;

/**
 * reactor上下文工具
 *
 * @author CPoet
 */
public abstract class ExchangeTool {

    public final static String WEB_EXCHANGE = "#WEB_EXCHANGE#";

    private ExchangeTool() {
    }

    public static ServerWebExchange getWebExchange(ContextView contextView) {
        return contextView.getOrDefault(WEB_EXCHANGE, null);
    }

    public static Context setWebExchange(Context context, ServerWebExchange webExchange) {
        return context.put(WEB_EXCHANGE, webExchange);
    }

    public static String getHeader(ContextView contextView, String key) {
        List<String> headers = getHeaders(contextView, key);
        return CollectionUtils.isEmpty(headers) ? null : headers.get(0);
    }

    public static String getHeader(ServerWebExchange webExchange, String key) {
        List<String> headers = getHeaders(webExchange, key);
        return CollectionUtils.isEmpty(headers) ? null : headers.get(0);
    }

    public static List<String> getHeaders(ContextView contextView, String key) {
        ServerWebExchange exchange = getWebExchange(contextView);
        return exchange == null ? null : getHeaders(exchange, key);
    }

    public static List<String> getHeaders(ServerWebExchange webExchange, String key) {
        ServerHttpRequest request = webExchange.getRequest();
        return request.getHeaders().get(key);
    }
}
