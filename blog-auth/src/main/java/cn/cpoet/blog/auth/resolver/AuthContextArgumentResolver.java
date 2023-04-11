package cn.cpoet.blog.auth.resolver;

import cn.cpoet.blog.api.context.AppContextHolder;
import cn.cpoet.blog.api.context.AuthContext;
import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证上下文方法参数注入
 *
 * @author wanggf
 */
public class AuthContextArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return AuthContext.class == methodParameter.getParameterType();
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter parameter, BindingContext bindingContext, ServerWebExchange exchange) {
        return Mono.fromSupplier(AppContextHolder::getAuthContext);
    }
}
