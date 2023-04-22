package cn.cpoet.blog.auth.util;

/**
 * 类型转换工具
 *
 * @author CPoet
 */
public abstract class TypeUtil {
    private TypeUtil() {
    }

    /**
     * 转换成long类型
     *
     * @param obj 对象
     * @return Long类型对象
     */
    public static Long toLong(Object obj) {
        String s = toString(obj);
        return s == null ? null : Long.parseLong(s);
    }

    /**
     * 转换成String类型
     *
     * @param obj 对象
     * @return String类型对象
     */
    public static String toString(Object obj) {
        return obj == null ? null : String.valueOf(obj);
    }
}
