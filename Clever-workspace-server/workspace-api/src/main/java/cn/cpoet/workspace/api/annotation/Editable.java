package cn.cpoet.workspace.api.annotation;

import java.lang.annotation.*;

/**
 * 标记实体属性是否是可编辑属性
 *
 * @author CPoet
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Editable {
    /**
     * 是否可编辑
     *
     * @return 是否可编辑
     */
    boolean value() default true;

    /**
     * 场景分组
     *
     * @return 场景分组
     */
    Class<?>[] groups() default {};
}
