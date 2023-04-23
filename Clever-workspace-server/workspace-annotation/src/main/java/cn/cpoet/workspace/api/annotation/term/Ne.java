package cn.cpoet.workspace.api.annotation.term;

import java.lang.annotation.*;

/**
 * 不等于
 *
 * @author CPoet
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ne {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";
}
