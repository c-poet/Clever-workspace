package cn.cpoet.blog.starter.admin.configuation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author CPoet
 */
@Configuration
public class StarterAdminConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 后台页面资源
        registry.addResourceHandler("/admin/**")
            .addResourceLocations("classpath:/static/admin/**");
    }
}
