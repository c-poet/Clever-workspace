package cn.cpoet.workspace.api.annotation.term;

import java.lang.annotation.*;

/**
 * in条件
 *
 * @author CPoet
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface In {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";
}
