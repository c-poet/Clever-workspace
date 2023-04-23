package cn.cpoet.blog.api.annotation.term;

import java.lang.annotation.*;

/**
 * 等于
 *
 * @author CPoet
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Eq {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";
}
