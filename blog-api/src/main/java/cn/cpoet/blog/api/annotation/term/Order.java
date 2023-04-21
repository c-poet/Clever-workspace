package cn.cpoet.blog.api.annotation.term;

import java.lang.annotation.*;

/**
 * 排序
 *
 * @author CPoet
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";

    /**
     * 是否倒序
     *
     * @return 是否倒序
     */
    boolean desc() default false;
}
