package cn.cpoet.blog.api.annotation.term;

/**
 * 大于
 *
 * @author CPoet
 */
public @interface Gt {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";
}
