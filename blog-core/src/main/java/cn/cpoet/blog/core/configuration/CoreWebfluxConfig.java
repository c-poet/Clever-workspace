package cn.cpoet.blog.core.configuration;

import cn.cpoet.blog.core.support.CustomResponseBodyHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;

/**
 * @author CPoet
 */
@Configuration
@RequiredArgsConstructor
public class CoreWebfluxConfig {

    @Bean
    public CustomResponseBodyHandler customResponseBodyHandler(
        @Qualifier("webFluxAdapterRegistry") ReactiveAdapterRegistry reactiveAdapterRegistry,
        ServerCodecConfigurer serverCodecConfigurer,
        @Qualifier("webFluxContentTypeResolver") RequestedContentTypeResolver contentTypeResolver) {
        CustomResponseBodyHandler responseBodyHandler = new CustomResponseBodyHandler(serverCodecConfigurer.getWriters(),
            contentTypeResolver, reactiveAdapterRegistry);
        responseBodyHandler.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return responseBodyHandler;
    }
}
