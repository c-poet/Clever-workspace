package cn.cpoet.workspace.api.annotation;

import java.lang.annotation.*;

/**
 * 枚举id，用于枚举的处理
 *
 * @author CPoet
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumId {
    /**
     * 自定义显示的名称
     *
     * @return 自定义显示名称
     */
    String value() default "";
}
