package cn.cpoet.workspace.api.audit;

import java.lang.annotation.*;

/**
 * @author CPoet
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UpdateBy {
}
