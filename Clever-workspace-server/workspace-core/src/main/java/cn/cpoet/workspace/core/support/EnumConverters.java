package cn.cpoet.workspace.core.support;

import cn.cpoet.workspace.api.context.AppContextHolder;
import cn.cpoet.workspace.api.core.EnumTool;
import cn.cpoet.workspace.api.exception.AppException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义枚举类型转化
 *
 * @author wanggf
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class EnumConverters {

    public final static Obj2EnumConverterFactory ENUM_CONVERTER_FACTORY;
    public final static List<Converter<?, ?>> CUSTOM_ENUM_CONVERTERS;

    static {
        ENUM_CONVERTER_FACTORY = new Obj2EnumConverterFactory();
        CUSTOM_ENUM_CONVERTERS = new ArrayList<>();
        CUSTOM_ENUM_CONVERTERS.add(new Enum2StrConverter());
        CUSTOM_ENUM_CONVERTERS.add(new Enum2IntegerConverter());
        CUSTOM_ENUM_CONVERTERS.add(new FakeEnumConverterImpl());
    }

    static class Obj2EnumConverterFactory implements ConverterFactory<Object, Enum> {

        private final static Map<Class<?>, Converter<Object, ? extends Enum>> CONVERTER_CACHE = new ConcurrentHashMap<>();

        @Override
        public <T extends Enum> Converter<Object, T> getConverter(Class<T> targetType) {
            return (Converter<Object, T>) CONVERTER_CACHE.computeIfAbsent(targetType, Obj2EnumConverter::new);
        }
    }

    static class Obj2EnumConverter<T extends Enum> implements Converter<Object, T> {
        private final Class<?> targetType;

        public Obj2EnumConverter(Class<?> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(Object source) {
            Object[] constants = targetType.getEnumConstants();
            if (constants.length == 0) {
                throw new AppException("未经实现的枚举类型");
            }
            EnumTool enumTool = AppContextHolder.getBean(EnumTool.class);
            if (enumTool.isTargetEnum(targetType)) {
                return (T) enumTool.enumOfId((Class<Enum>) targetType, source);
            }
            if (source instanceof Integer) {
                int index = (int) source;
                if (index > 0 && index < constants.length) {
                    return (T) constants[index];
                }
            }
            return (T) Enum.valueOf((Class<Enum>) targetType, source.toString());
        }
    }

    static class FakeEnumConverterImpl extends Obj2EnumConverter<Enum> {
        public FakeEnumConverterImpl() {
            super(Enum.class);
        }
    }

    static class Enum2StrConverter implements Converter<Enum, String> {
        @Override
        public String convert(Enum source) {
            EnumTool enumTool = AppContextHolder.getBean(EnumTool.class);
            if (enumTool.isTargetEnum(source.getClass())) {
                return String.valueOf(enumTool.getId(source));
            }
            return source.name();
        }
    }

    static class Enum2IntegerConverter implements Converter<Enum, Integer> {
        @Override
        public Integer convert(Enum source) {
            EnumTool enumTool = AppContextHolder.getBean(EnumTool.class);
            if (enumTool.isTargetEnum(source.getClass())) {
                Object id = enumTool.getId(source);
                if (id instanceof Integer) {
                    return (Integer) id;
                }
            }
            Enum[] constants = source.getClass().getEnumConstants();
            if (constants != null && constants.length != 0) {
                int index = 0;
                for (Enum constant : constants) {
                    if (constant.equals(source)) {
                        return index;
                    }
                    ++index;
                }
            }
            throw new AppException("未经定义的枚举类型");
        }
    }
}
