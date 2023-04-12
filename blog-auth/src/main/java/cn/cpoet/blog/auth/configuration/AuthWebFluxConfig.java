package cn.cpoet.blog.auth.configuration;

import cn.cpoet.blog.auth.resolver.SubjectArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

/**
 * 解析器相关配置
 *
 * @author wanggf
 */
@Configuration
@RequiredArgsConstructor
public class AuthWebFluxConfig implements WebFluxConfigurer {

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(new SubjectArgumentResolver());
    }
}
