package cn.cpoet.workspace.auth.configuration;

import cn.cpoet.workspace.auth.resolver.SubjectArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 解析器相关配置
 *
 * @author wanggf
 */
@Configuration
@RequiredArgsConstructor
public class AuthWebFluxConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SubjectArgumentResolver());
    }
}
