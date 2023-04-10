package cn.cpoet.blog.core.configuration;

import cn.cpoet.blog.core.ibatis.IBatisEnumTypeHandler;
import cn.cpoet.blog.core.ibatis.IBatisObjectWrapperFactory;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置
 *
 * @author CPoet
 */
@Configuration
public class IBatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 兼容处理
            configuration.setObjectWrapperFactory(new IBatisObjectWrapperFactory());
            // 下划线转驼峰
            configuration.setMapUnderscoreToCamelCase(true);
            // 设置默认的枚举处理类
            configuration.setDefaultEnumTypeHandler(IBatisEnumTypeHandler.class);
        };
    }
}
