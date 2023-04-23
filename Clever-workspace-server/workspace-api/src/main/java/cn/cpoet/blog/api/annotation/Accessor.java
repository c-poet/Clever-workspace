package cn.cpoet.blog.api.annotation;

import cn.cpoet.blog.api.constant.Logic;
import cn.cpoet.blog.api.constant.SubjectType;
import cn.cpoet.blog.api.core.AccessorHandler;

import java.lang.annotation.*;

/**
 * 访问权限控制
 *
 * @author CPoet
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Accessor.List.class)
@Documented
public @interface Accessor {
    /**
     * 权限code集合
     *
     * @return 权限code集合
     */
    String[] value() default {};

    /**
     * 逻辑关系
     *
     * @return 逻辑关系
     */
    Logic logic() default Logic.AND;

    /**
     * subject类型
     *
     * @return subject类型
     */
    SubjectType[] subjects() default {};

    /**
     * 自定义处理器
     *
     * @return 自定义处理器
     */
    Class<? extends AccessorHandler> handler() default AccessorHandler.class;

    /**
     * 列表，采用OR逻辑
     */
    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        /**
         * 访问权限控制
         *
         * @return 访问权限
         */
        Accessor[] value() default {};
    }
}
