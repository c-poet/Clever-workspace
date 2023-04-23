package cn.cpoet.workspace.api.core;

import cn.cpoet.workspace.api.annotation.Accessor;
import cn.cpoet.workspace.api.context.Subject;

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
     * @param subject  当前访问主体
     * @param obj      访问目标对象
     * @param method   访问目标方法
     * @param meta     访问元信息
     * @param accessor 当前访问信息
     */
    void handle(Subject subject, Object obj, Method method, AccessorMeta meta, Accessor accessor);
}
