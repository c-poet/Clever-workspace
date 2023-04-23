package cn.cpoet.workspace.api.annotation;

import java.lang.annotation.*;

/**
 * 模块定义
 *
 * @author CPoet
 */
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Module {
    /**
     * 模块名称
     *
     * @return 模块名称
     */
    String name();

    /**
     * 模块介绍
     *
     * @return 模块介绍
     */
    String description() default "";

    /**
     * 模块下的bean信息重写
     *
     * @return bean信息重写
     */
    Rewrite rewrite() default @Rewrite;

    /**
     * 路径绑定信息
     *
     * @return 路径绑定信息
     */
    Mapping mapping() default @Mapping(path = "");
}
