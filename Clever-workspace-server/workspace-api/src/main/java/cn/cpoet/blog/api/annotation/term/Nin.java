package cn.cpoet.blog.api.annotation.term;

import java.lang.annotation.*;

/**
 * Not In条件
 *
 * @author CPoet
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Nin {
    /**
     * 属性名
     *
     * @return 属性名
     */
    String value() default "";
}
