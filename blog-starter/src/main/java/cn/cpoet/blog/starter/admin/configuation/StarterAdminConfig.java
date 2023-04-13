package cn.cpoet.blog.starter.admin.configuation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;

/**
 * @author CPoet
 */
@Configuration
public class StarterAdminConfig extends WebFluxConfigurationSupport {
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 后台页面资源
        registry.addResourceHandler("/admin/**")
            .addResourceLocations("classpath:/static/admin/**");
    }
}
