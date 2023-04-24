package cn.cpoet.workspace.ibatis.configuration;

import cn.cpoet.workspace.ibatis.support.IBatisEnumTypeHandler;
import cn.cpoet.workspace.ibatis.support.IBatisObjectWrapperFactory;
import com.github.pagehelper.PageInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.mapper.autoconfigure.ConfigurationCustomizer;

/**
 * @author CPoet
 */
@ComponentScan(basePackages = "cn.cpoet.workspace.ibatis")
public class IBatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            // 分页插件
            configuration.addInterceptor(new PageInterceptor());
            // 兼容处理
            configuration.setObjectWrapperFactory(new IBatisObjectWrapperFactory());
            // 下划线转驼峰
            configuration.setMapUnderscoreToCamelCase(true);
            // 设置默认的枚举处理类
            configuration.setDefaultEnumTypeHandler(IBatisEnumTypeHandler.class);
        };
    }
}
