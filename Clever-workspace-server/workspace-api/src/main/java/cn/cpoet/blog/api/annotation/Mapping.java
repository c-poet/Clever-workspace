package cn.cpoet.blog.api.annotation;

import java.lang.annotation.*;

/**
 * 绑定路径信息
 *
 * @author CPoet
 */
@Target(ElementType.PACKAGE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mapping {
    /**
     * 路径
     *
     * @return 路径
     */
    String path();
}
