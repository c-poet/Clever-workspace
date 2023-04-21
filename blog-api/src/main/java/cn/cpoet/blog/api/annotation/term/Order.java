package cn.cpoet.blog.api.annotation.term;

/**
 * 排序
 *
 * @author CPoet
 */
public @interface Order {
    /**
     * 字段名
     *
     * @return 字段名
     */
    String value() default "";

    /**
     * 是否倒序
     *
     * @return 是否倒序
     */
    boolean desc() default false;
}
