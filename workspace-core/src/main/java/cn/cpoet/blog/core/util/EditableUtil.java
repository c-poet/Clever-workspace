package cn.cpoet.blog.core.util;

import cn.cpoet.blog.api.annotation.Editable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 可编辑标识工具
 *
 * @author CPoet
 */
public abstract class EditableUtil {
    private final static Map<Class<?>, String[]> IGNORE_FIELD_CACHE = new ConcurrentHashMap<>();

    private EditableUtil() {
    }

    /**
     * 获取跳过编辑的字段
     *
     * @param obj    对象
     * @param groups 场景组
     * @return 字段数组
     */
    public static String[] getIgnoreFieldByObj(Object obj, Class<?>... groups) {
        return obj == null ? new String[0] : getIgnoreField(obj.getClass(), groups);
    }

    /**
     * 获取跳过编辑的字段
     *
     * @param clazz  对象类型
     * @param groups 场景组
     * @return 字段数组
     */
    public static String[] getIgnoreField(Class<?> clazz, Class<?>... groups) {
        if (clazz == Object.class) {
            return new String[0];
        }
        String[] ignoreFields = IGNORE_FIELD_CACHE.get(clazz);
        if (ignoreFields != null) {
            return ignoreFields;
        }
        Editable clazzEditable = clazz.getDeclaredAnnotation(Editable.class);
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length == 0) {
            return new String[0];
        }
        List<String> ignoreFieldList = new ArrayList<>();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers())) {
                Editable fieldEditable = field.getAnnotation(Editable.class);
                if (!isEditable(clazzEditable, fieldEditable, groups)) {
                    ignoreFieldList.add(field.getName());
                }
            }
        }
        String[] supperIgnoreFields = getIgnoreField(clazz.getSuperclass(), groups);
        if (supperIgnoreFields.length > 0) {
            ignoreFieldList.addAll(Arrays.asList(supperIgnoreFields));
        }
        ignoreFields = ignoreFieldList.toArray(new String[0]);
        IGNORE_FIELD_CACHE.put(clazz, ignoreFields);
        return ignoreFields;
    }

    /**
     * 判断是否可编辑
     *
     * @param classEditable 类编辑注解
     * @param fieldEditable 属性编辑注解
     * @param groups        分组
     * @return 是否可编辑
     */
    private static boolean isEditable(Editable classEditable, Editable fieldEditable, Class<?>... groups) {
        if (classEditable == null) {
            return fieldEditable != null && isEditable(fieldEditable, groups);
        }
        if (fieldEditable == null) {
            return isEditable(classEditable, groups);
        }
        return isEditable(classEditable, groups) && isEditable(fieldEditable, groups);
    }

    /**
     * 判断是否可编辑
     *
     * @param editable 编辑注解
     * @param groups   分组
     * @return 是否可编辑
     */
    private static boolean isEditable(Editable editable, Class<?>... groups) {
        Class<?>[] editableGroups = editable.groups();
        if (groups.length == 0) {
            return editableGroups.length == 0 && editable.value();
        }
        for (Class<?> group : groups) {
            for (Class<?> editableGroup : editableGroups) {
                if (group == editableGroup) {
                    return editable.value();
                }
            }
        }
        return !editable.value();
    }
}
