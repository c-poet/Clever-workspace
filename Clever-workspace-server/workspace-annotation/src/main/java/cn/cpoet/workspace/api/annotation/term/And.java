package cn.cpoet.workspace.api.annotation.term;

import java.lang.annotation.*;

/**
 * 逻辑And
 *
 * @author CPoet
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface And {
}
