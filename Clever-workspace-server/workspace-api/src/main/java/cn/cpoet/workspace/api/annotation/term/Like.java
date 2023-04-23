package cn.cpoet.workspace.api.annotation.term;

import java.lang.annotation.*;

/**
 * like
 *
 * @author CPoet
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Like {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";

    /**
     * 左like
     *
     * @return 左like
     */
    boolean left() default true;

    /**
     * 右like
     *
     * @return 右like
     */
    boolean right() default true;
}
