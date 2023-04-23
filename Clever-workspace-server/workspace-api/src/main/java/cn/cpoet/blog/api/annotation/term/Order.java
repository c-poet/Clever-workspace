package cn.cpoet.blog.api.annotation.term;

import cn.cpoet.blog.api.constant.OrderMode;

import java.lang.annotation.*;

/**
 * 排序
 *
 * @author CPoet
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Order {
    /**
     * 默认的模式
     *
     * @return 默认模式
     */
    OrderMode value() default OrderMode.ASC;

    /**
     * 默认的排序字段
     * <p>变量未设定时才使用该值</p>
     *
     * @return 默认的排序字段
     */
    String[] fields() default {};
}
