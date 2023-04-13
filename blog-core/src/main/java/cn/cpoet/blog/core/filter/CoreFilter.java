package cn.cpoet.blog.core.filter;

import cn.cpoet.blog.api.core.ExchangeTool;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * 核心过滤器
 *
 * @author CPoet
 */
@Component
public class CoreFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain
            .filter(exchange)
            // 在上下文中传递exchange
            .contextWrite(ctx -> ExchangeTool.setWebExchange(ctx, exchange));
    }
}
