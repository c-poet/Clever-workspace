package cn.cpoet.blog.core.context;

import cn.cpoet.blog.api.context.WebContext;
import org.springframework.web.server.ServerWebExchange;

/**
 * 请求上下文工厂
 *
 * @author CPoet
 */
public abstract class RequestContextFactory {
    private RequestContextFactory() {
    }

    /**
     * 创建请求上下文
     *
     * @param exchange 请求信息
     * @return 请求上下文
     */
    public static WebContext create(ServerWebExchange exchange) {
        return new SimpleWebContext(exchange);
    }
}
