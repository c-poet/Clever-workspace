package cn.cpoet.blog.core.configuration;

import cn.cpoet.blog.core.support.Jackson2JsonEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author CPoet
 */
@Configuration
@RequiredArgsConstructor
public class CoreWebfluxConfig implements WebFluxConfigurer {

    private final ObjectMapper objectMapper;

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer
            .defaultCodecs()
            .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
    }
}
