package cn.cpoet.workspace.api.annotation;

import java.lang.annotation.*;

/**
 * 枚举字典标记
 *
 * @author CPoet
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumDict {
    /**
     * 自定义字典名称
     *
     * @return 字典名称
     */
    String value() default "";
}
