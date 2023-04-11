package cn.cpoet.blog.core.configuration;

import cn.cpoet.blog.core.resolver.AppContextArgumentResolver;
import cn.cpoet.blog.core.resolver.RequestContextArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

/**
 * @author CPoet
 */
@Configuration
public class CoreWebFluxConfig implements WebFluxConfigurer {
    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(new AppContextArgumentResolver());
        configurer.addCustomResolver(new RequestContextArgumentResolver());
    }
}
