package cn.cpoet.blog.auth.configuration;

import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.auth.configuration.auth.AuthProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author CPoet
 */
@ComponentScan("cn.cpoet.blog.auth")
public class AuthConfig {
    @Bean
    @ConfigurationProperties(prefix = SystemConst.CONF_PREFIX + ".auth")
    public AuthProperties authProperties() {
        return new AuthProperties();
    }
}
