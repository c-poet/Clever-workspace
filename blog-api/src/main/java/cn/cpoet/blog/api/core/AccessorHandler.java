package cn.cpoet.blog.api.core;

import cn.cpoet.blog.api.context.Subject;

import java.lang.reflect.Method;

/**
 * 访问权限控制处理器
 *
 * @author CPoet
 */
@FunctionalInterface
public interface AccessorHandler {
    /**
     * 是否允许访问
     *
     * @param subject 当前访问主体
     * @param obj     访问目标对象
     * @param method  访问目标方法
     * @param meta    访问元信息
     * @return 是否允许访问
     */
    boolean accept(Subject subject, Object obj, Method method, AccessorMeta meta);
}
