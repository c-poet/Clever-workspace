package cn.cpoet.blog.api.annotation.term;

/**
 * 等于
 *
 * @author CPoet
 */
public @interface Eq {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";
}
