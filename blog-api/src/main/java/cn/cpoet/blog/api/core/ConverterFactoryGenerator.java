package cn.cpoet.blog.api.core;

import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 转换工厂生成器
 *
 * @author CPoet
 */
@SuppressWarnings("rawtypes")
public interface ConverterFactoryGenerator {
    /**
     * 普通对象转枚举
     *
     * @return 枚举值
     */
    ConverterFactory<Object, Enum> getObj2EnumFactory();

    /**
     * 枚举转普通对象
     *
     * @return 对象值
     */
    ConverterFactory<Enum, Object> getEnum2ObjFactory();
}
