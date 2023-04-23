package cn.cpoet.workspace.auth.util;

import java.lang.reflect.Method;

/**
 * 方法工具
 *
 * @author CPoet
 */
public abstract class MethodUtil {
    private MethodUtil() {
    }

    /**
     * 访问方法特征id
     *
     * @param clazz  方法所在类
     * @param method 方法
     * @return 方法特征id
     */
    public static String getMethodId(Class<?> clazz, Method method) {
        return clazz.getName() + "." + method;
    }
}
