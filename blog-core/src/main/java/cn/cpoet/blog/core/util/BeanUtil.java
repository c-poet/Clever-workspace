package cn.cpoet.blog.core.util;

import org.springframework.beans.BeanUtils;

/**
 * Bean操作工具
 *
 * @author CPoet
 */
public abstract class BeanUtil {
    private BeanUtil() {
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
