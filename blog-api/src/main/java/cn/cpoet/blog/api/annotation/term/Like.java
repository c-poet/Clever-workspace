package cn.cpoet.blog.api.annotation.term;

/**
 * like
 *
 * @author CPoet
 */
public @interface Like {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";

    /**
     * 左like
     *
     * @return 左like
     */
    boolean left() default true;

    /**
     * 右like
     *
     * @return 右like
     */
    boolean right() default true;
}
