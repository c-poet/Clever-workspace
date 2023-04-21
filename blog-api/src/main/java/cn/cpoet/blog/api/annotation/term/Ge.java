package cn.cpoet.blog.api.annotation.term;

/**
 * 大于等于
 *
 * @author CPoet
 */
public @interface Ge {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";
}
