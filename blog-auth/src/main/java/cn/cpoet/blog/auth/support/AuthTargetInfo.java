package cn.cpoet.blog.auth.support;

import java.lang.reflect.Method;

/**
 * 代理信息
 *
 * @author CPoet
 */
public class AuthTargetInfo {

    /**
     * 获取方法唯一id
     *
     * @param method 方法对象
     * @return 方法id
     */
    public static String getMethodId(Method method) {
        if (method == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(method.getName());
        final Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 0) {
            for (Class<?> parameterType : parameterTypes) {
                sb.append(parameterType.getName());
            }
        }
        return sb.toString();
    }
}
