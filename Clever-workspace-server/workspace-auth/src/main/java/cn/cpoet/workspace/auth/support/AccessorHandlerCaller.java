package cn.cpoet.workspace.auth.support;

import cn.cpoet.workspace.api.annotation.Accessor;
import cn.cpoet.workspace.api.context.Subject;
import cn.cpoet.workspace.api.core.AccessorHandler;
import cn.cpoet.workspace.api.core.AccessorMeta;
import cn.cpoet.workspace.auth.exception.AuthException;

import java.lang.reflect.Method;

/**
 * 访问处理对象调用
 *
 * @author CPoet
 */
public class AccessorHandlerCaller {

    public final static AccessorHandlerCaller INSTANCE = new AccessorHandlerCaller();

    /**
     * 调用处理器
     *
     * @param obj    方法所在类实例
     * @param method 方法
     * @param meta   认证信息
     */
    public void call(Object obj, Method method, AccessorMeta meta) {
        // 当前认证主体
//        Subject subject = AppContextHolder.getAuthContext().curSubject();
//        Accessor[] beanAccessor = meta.getBeanAccessor();
//        if (beanAccessor != null) {
//            call(subject, obj, method, meta, beanAccessor);
//        }
//        Accessor[] methodAccessor = meta.getMethodAccessor();
//        if (methodAccessor != null) {
//            call(subject, obj, method, meta, methodAccessor);
//        }
    }

    /**
     * 调用处理器
     *
     * @param subject   主体
     * @param obj       方法所在类实例
     * @param method    方法
     * @param meta      认证信息
     * @param accessors 访问信息
     */
    private void call(Subject subject, Object obj, Method method, AccessorMeta meta, Accessor[] accessors) {
        StringBuilder sb = new StringBuilder();
        for (Accessor accessor : accessors) {
            AccessorHandler accessorHandler = AccessorHandlerFactory.get(accessor.handler());
            try {
                accessorHandler.handle(subject, obj, method, meta, accessor);
                return;
            } catch (Exception e) {
                sb.append(e.getMessage());
            }
        }
        throw new AuthException(sb.toString());
    }
}
