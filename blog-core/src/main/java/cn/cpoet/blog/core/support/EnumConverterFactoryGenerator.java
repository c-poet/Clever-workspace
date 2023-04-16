package cn.cpoet.blog.core.support;

import cn.cpoet.blog.api.core.ConverterFactoryGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/**
 * 自定义枚举类型转化
 *
 * @author CPoet
 */
@Component
@RequiredArgsConstructor
@SuppressWarnings({"rawtypes", "unchecked"})
public class EnumConverterFactoryGenerator implements ConverterFactoryGenerator {

    private final EnumHandler enumHandler;

    @Override
    public ConverterFactory<Object, Enum> getObj2EnumFactory() {
        return new ConverterFactory<Object, Enum>() {
            @Override
            public <T extends Enum> Converter<Object, T> getConverter(Class<T> targetType) {
                return source -> (T) enumHandler.enumOfId(targetType, source);
            }
        };
    }

    @Override
    public ConverterFactory<Enum, Object> getEnum2ObjFactory() {
        return new ConverterFactory<Enum, Object>() {
            @Override
            public <T> Converter<Enum, T> getConverter(Class<T> targetType) {
                return source -> (T) enumHandler.getId(source);
            }
        };
    }
}
