package cn.cpoet.workspace.core.configuration;

import cn.cpoet.workspace.core.support.EnumConverters;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author CPoet
 */
@ComponentScan("cn.cpoet.workspace.core")
public class CoreConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.removeConvertible(String.class, Enum.class);
        registry.removeConvertible(Enum.class, String.class);
        registry.removeConvertible(Integer.class, Enum.class);
        registry.removeConvertible(Enum.class, Integer.class);
        registry.addConverterFactory(EnumConverters.ENUM_CONVERTER_FACTORY);
        EnumConverters.CUSTOM_ENUM_CONVERTERS.forEach(registry::addConverter);
    }
}
