package cn.cpoet.blog.core.filter;

import cn.cpoet.blog.api.context.WebContext;
import cn.cpoet.blog.api.context.RequestContextHolder;
import cn.cpoet.blog.core.context.RequestContextFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 核心过滤器
 *
 * @author CPoet
 */
@Component
public class CoreFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            WebContext requestContext = RequestContextFactory.create(exchange);
            RequestContextHolder.set(requestContext);
            return chain.filter(exchange);
        } finally {
            // 移出当前请求上下文信息
            RequestContextHolder.clear();
        }
    }
}
