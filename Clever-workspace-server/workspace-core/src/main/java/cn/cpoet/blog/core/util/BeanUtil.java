package cn.cpoet.blog.core.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bean操作工具
 *
 * @author CPoet
 */
public abstract class BeanUtil {
    private BeanUtil() {
    }

    /**
     * 获取所有属性列表
     *
     * @param clazz 类型
     * @return 属性列表
     */
    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 获取get方法名称
     *
     * @param beanClass 类
     * @param field     属性
     * @return get方法名称
     */
    public static String getIntactReadMethodName(Class<?> beanClass, Field field) {
        String readMethodName = getReadMethodName(beanClass, field);
        return StringUtils.hasText(readMethodName) ? readMethodName + "()" : readMethodName;
    }

    /**
     * 获取get方法名称
     *
     * @param beanClass 类
     * @param field     属性
     * @return get方法名称
     */
    public static String getReadMethodName(Class<?> beanClass, Field field) {
        PropertyDescriptor descriptor = BeanUtils.getPropertyDescriptor(beanClass, field.getName());
        if (descriptor == null) {
            return null;
        }
        Method readMethod = descriptor.getReadMethod();
        return readMethod == null ? null : readMethod.getName();
    }

    /**
     * 拷贝属性并返回对象
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <T>    目标对象类型
     * @return 目标对象
     */
    public static <T> T copyProperties(Object source, T target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 拷贝属性并返回对象
     *
     * @param source           源对象
     * @param target           目标对象
     * @param ignoreProperties 跳过拷贝的属性
     * @param <T>              目标对象类型
     * @return 目标对象
     */
    public static <T> T copyProperties(Object source, T target, String... ignoreProperties) {
        BeanUtils.copyProperties(source, target, ignoreProperties);
        return target;
    }

    /**
     * 拷贝可编辑的属性
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <T>    目标对象类型
     * @return 目标对象
     */
    public static <T> T copyEditableProperties(Object source, T target) {
        return copyProperties(source, target, EditableUtil.getIgnoreFieldByObj(target));
    }
}
