package cn.cpoet.blog.api.annotation.term;

import java.lang.annotation.*;

/**
 * 逻辑Or
 *
 * @author CPoet
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Or {
}
