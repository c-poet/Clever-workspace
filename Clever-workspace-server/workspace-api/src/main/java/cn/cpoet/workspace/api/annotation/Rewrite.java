package cn.cpoet.workspace.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 重写bean信息
 *
 * @author CPoet
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Rewrite {
    /**
     * 是否开启重写
     *
     * @return 是否开启重写
     */
    boolean value() default true;

    /**
     * 是否获取上级包情况
     *
     * @return 是否获取上级包情况
     */
    boolean parent() default true;

    /**
     * 生成bean的前缀，默认取模块名
     *
     * @return bean前缀
     */
    String prefix() default "";

    /**
     * 重写bean名时前缀和原名的分隔符
     *
     * @return 重写bean名时前缀和原名的分隔符
     */
    String separator() default "@";
}
