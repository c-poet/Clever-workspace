package cn.cpoet.blog.core.support;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Component;

/**
 * @author CPoet
 */
@Component("conversionService")
@RequiredArgsConstructor
@SuppressWarnings({"rawtypes", "unchecked"})
public class EnumConversionServiceImpl implements ConversionService {

    private final EnumHandler enumHandler;
    private final ConversionService inner = new DefaultConversionService();

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return inner.canConvert(sourceType, targetType);
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return inner.canConvert(sourceType, targetType);
    }

    @Override
    public <T> T convert(Object source, Class<T> targetType) {
        if (source != null && isCustomEnumConvert(source.getClass(), targetType)) {
            return handleCustomEnumConvert(source, targetType);
        }
        return inner.convert(source, targetType);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        if (source != null && sourceType != null && isCustomEnumConvert(sourceType.getType(), targetType.getType())) {
            return handleCustomEnumConvert(source, targetType.getType());
        }
        return inner.convert(source, sourceType, targetType);
    }

    private <T> T handleCustomEnumConvert(Object source, Class<T> targetType) {
        return targetType.isEnum()
            ? (T) enumHandler.enumOfId((Class) targetType, source)
            : (T) enumHandler.getId((Enum) source);
    }

    private boolean isCustomEnumConvert(Class<?> sourceType, Class<?> targetType) {
        return sourceType != null
            && ((!targetType.isEnum() && enumHandler.isTargetEnum(sourceType))
            || (!sourceType.isEnum() && enumHandler.isTargetEnum(targetType)));
    }
}
