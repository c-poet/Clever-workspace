package cn.cpoet.blog.api.annotation;

import java.lang.annotation.*;

/**
 * 枚举值呈现注解
 *
 * @author CPoet
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumAppear {
    /**
     * 自定义显示名称
     *
     * @return 自定义显示名称
     */
    String value() default "";
}
