package cn.cpoet.workspace.api.core;

import cn.cpoet.workspace.api.annotation.EnumAppear;
import cn.cpoet.workspace.api.annotation.EnumId;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author CPoet
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EnumTool {

    private final static String READER_METHOD_PREFIX = "get";

    private final static Map<Class<?>, Class<?>> TYPE_PACK_MAPPER = new HashMap<>();

    /**
     * 枚举id类型缓存
     */
    private final static Map<Class<?>, EnumMeta> ENUM_ID_META_CACHE = new ConcurrentHashMap<>();
    private final static Map<Class<?>, Map<String, Enum>> ENUM_CACHE = new ConcurrentHashMap<>();

    static {
        TYPE_PACK_MAPPER.put(int.class, Integer.class);
        TYPE_PACK_MAPPER.put(byte.class, Byte.class);
        TYPE_PACK_MAPPER.put(boolean.class, Boolean.class);
        TYPE_PACK_MAPPER.put(float.class, Float.class);
        TYPE_PACK_MAPPER.put(long.class, Long.class);
        TYPE_PACK_MAPPER.put(double.class, Double.class);
        TYPE_PACK_MAPPER.put(char.class, Character.class);
    }

    public static <T extends Enum<T>> Object getId(Enum<T> enumObj) {
        final EnumMeta enumMeta = getEnumMeta(enumObj.getClass());
        try {
            return enumMeta.getIdMethod().invoke(enumObj);
        } catch (Exception e) {
            throw new IllegalStateException("枚举Id值获取失败", e);
        }
    }

    public static Class<?> getIdType(Class<? extends Enum> clazz) {
        return getEnumMeta(clazz).getIdClass();
    }

    public static boolean isTargetEnum(Class<?> clazz) {
        return clazz != null && clazz.isEnum() && getEnumMeta((Class) clazz) != EnumMeta.EMPTY;
    }

    public static <T extends Enum<T>> GenMap getEnumAppear(Enum<T> enumObj) {
        final EnumMeta enumMeta = getEnumMeta(enumObj.getClass());
        try {
            GenMap enumAppear = new GenMap();
            final Object idValue = enumMeta.getIdMethod().invoke(enumObj);
            enumAppear.put(enumMeta.getIdName(), idValue);
            for (Map.Entry<String, Method> entry : enumMeta.getAppearMap().entrySet()) {
                final Object appearValue = entry.getValue().invoke(enumObj);
                enumAppear.put(entry.getKey(), appearValue);
            }
            return enumAppear;
        } catch (Exception e) {
            throw new IllegalStateException("获取枚举信息失败", e);
        }
    }

    public static <T extends Enum<T>> T enumOfId(Class<T> tClass, Object id) {
        Map<String, Enum> objectEnumMap = ENUM_CACHE.get(tClass);
        if (CollectionUtils.isEmpty(objectEnumMap)) {
            final EnumMeta enumMeta = getEnumMeta(tClass);
            final T[] enumConstants = tClass.getEnumConstants();
            if (enumConstants.length == 0) {
                throw new IllegalStateException("未定义枚举值");
            }
            try {
                objectEnumMap = new HashMap<>(enumConstants.length);
                for (T enumConstant : enumConstants) {
                    final Object enumId = enumMeta.getIdMethod().invoke(enumConstant);
                    objectEnumMap.put(String.valueOf(enumId), enumConstant);
                }
                ENUM_CACHE.put(tClass, objectEnumMap);
            } catch (Exception e) {
                throw new IllegalStateException("读取枚举id值失败", e);
            }
        }
        final Enum anEnum = objectEnumMap.get(String.valueOf(id));
        if (anEnum == null) {
            throw new IllegalStateException("未找到Id值为" + id + "的枚举");
        }
        return (T) anEnum;
    }

    private static <T extends Enum<T>> EnumMeta getEnumMeta(Class<T> tClass) {
        EnumMeta enumMeta = ENUM_ID_META_CACHE.get(tClass);
        if (enumMeta != null) {
            return enumMeta;
        }
        final Field[] fields = tClass.getDeclaredFields();
        if (fields.length > 0) {
            Field enumIdField = null;
            List<Field> appearFields = new ArrayList<>();
            for (Field field : fields) {
                final EnumId enumId = field.getDeclaredAnnotation(EnumId.class);
                if (enumId != null) {
                    enumIdField = field;
                } else {
                    final EnumAppear enumAppear = field.getDeclaredAnnotation(EnumAppear.class);
                    if (enumAppear != null) {
                        appearFields.add(field);
                    }
                }
            }
            if (enumIdField != null) {
                enumMeta = new EnumMeta();
                final String idFieldName = enumIdField.getName();
                enumMeta.setIdName(idFieldName);
                Method fieldGetMethod = findFieldGetMethod(tClass, idFieldName);
                enumMeta.setIdClass(getPackageClass(enumIdField.getType()));
                enumMeta.setIdMethod(fieldGetMethod);
                // 其他展示的字段
                if (!CollectionUtils.isEmpty(appearFields)) {
                    Map<String, Method> appearMap = new HashMap<>(appearFields.size());
                    for (Field appearField : appearFields) {
                        final String fieldName = appearField.getName();
                        appearMap.put(fieldName, findFieldGetMethod(tClass, fieldName));
                    }
                    enumMeta.setAppearMap(appearMap);
                } else {
                    enumMeta.setAppearMap(Collections.emptyMap());
                }
            }
        }
        if (enumMeta == null) {
            enumMeta = EnumMeta.EMPTY;
        }
        ENUM_ID_META_CACHE.put(tClass, enumMeta);
        return enumMeta;
    }

    private static Class<?> getPackageClass(Class<?> clazz) {
        if (!clazz.isPrimitive()) {
            return clazz;
        }
        return TYPE_PACK_MAPPER.get(clazz);
    }

    private static Method findFieldGetMethod(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredMethod(fieldName);
        } catch (NoSuchMethodException ignored) {
        }
        String targetMethodName = READER_METHOD_PREFIX + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        try {
            return clazz.getDeclaredMethod(targetMethodName);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("查找属性read方法失败", e);
        }
    }

    /**
     * 枚举元信息
     */
    @Data
    private static class EnumMeta {

        public final static EnumMeta EMPTY = new EnumMeta();

        private Class<?> idClass;

        private String idName;

        private Method idMethod;

        private Map<String, Method> appearMap;
    }
}
