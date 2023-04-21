package cn.cpoet.blog.api.annotation.term;

/**
 * 不等于
 *
 * @author CPoet
 */
public @interface Ne {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";
}
