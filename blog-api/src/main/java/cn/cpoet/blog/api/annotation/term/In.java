package cn.cpoet.blog.api.annotation.term;

/**
 * in条件
 *
 * @author CPoet
 */
public @interface In {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";
}
