package cn.cpoet.blog.core.mongo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;

/**
 * 自定义mongo类型处理
 *
 * @author CPoet
 */
public class CustomMappingMongoConverter extends MappingMongoConverter {
    public CustomMappingMongoConverter(DbRefResolver dbRefResolver,
                                       MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext) {
        super(dbRefResolver, mappingContext);
        this.executeDefaultEnumConverter();
    }

    /**
     * 添加类型转换器工厂
     *
     * @param factory 类型转换器工厂
     */
    public void addConverterFactory(ConverterFactory<?, ?> factory) {
        conversionService.addConverterFactory(factory);
    }

    /**
     * 添加自定义类型转换器
     *
     * @param converter 转换器
     */
    public void addConverter(Converter<?, ?> converter) {
        conversionService.addConverter(converter);
    }

    /**
     * 排除默认的枚举转换器
     * <p>调用{@link #addConverterFactory(ConverterFactory)}和{@link #addConverter(Converter)}添加</p>
     */
    protected void executeDefaultEnumConverter() {
        conversionService.removeConvertible(String.class, Enum.class);
        conversionService.removeConvertible(Enum.class, String.class);
        conversionService.removeConvertible(Integer.class, Enum.class);
        conversionService.removeConvertible(Enum.class, Integer.class);
    }
}
