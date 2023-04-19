package cn.cpoet.blog.api.annotation;

import java.lang.annotation.*;

/**
 * 将枚举标记成字典
 *
 * @author CPoet
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Dict {
    /**
     * 字典名称
     *
     * @return 字典名称
     */
    String value() default "";
}
