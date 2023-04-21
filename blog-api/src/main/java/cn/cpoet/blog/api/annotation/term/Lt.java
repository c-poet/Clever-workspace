package cn.cpoet.blog.api.annotation.term;

/**
 * 小于
 *
 * @author CPoet
 */
public @interface Lt {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";
}
