package cn.cpoet.blog.api.core;

import cn.cpoet.blog.api.annotation.Accessor;
import cn.cpoet.blog.api.context.Subject;

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
     * @param accessor 访问配置
     * @return 是否允许访问
     */
    boolean accept(Subject subject, Accessor accessor);
}
