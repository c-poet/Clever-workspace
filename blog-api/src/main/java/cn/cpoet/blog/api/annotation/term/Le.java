package cn.cpoet.blog.api.annotation.term;

/**
 * 小于等于
 *
 * @author CPoet
 */
public @interface Le {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";
}
