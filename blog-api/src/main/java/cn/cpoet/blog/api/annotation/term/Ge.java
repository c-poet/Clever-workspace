package cn.cpoet.blog.api.annotation.term;

import java.lang.annotation.*;

/**
 * 大于等于
 *
 * @author CPoet
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ge {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";
}
