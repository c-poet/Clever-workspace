package cn.cpoet.blog.core.support;

import org.springframework.boot.autoconfigure.web.reactive.WebFluxRegistrations;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

/**
 * 自定义webflux
 *
 * @author CPoet
 */
@Component
public class CustomWebFluxRegistrations implements WebFluxRegistrations {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping = new CustomRequestMappingHandlerMapping();

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return requestMappingHandlerMapping;
    }
}
