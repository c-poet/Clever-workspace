package cn.cpoet.blog.core.filter;

import cn.cpoet.blog.api.core.ExchangeTool;
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
        return Mono.deferContextual(context -> {
            ExchangeTool.setWebExchange((Context) context, exchange);
            return chain.filter(exchange);
        });
    }
}
