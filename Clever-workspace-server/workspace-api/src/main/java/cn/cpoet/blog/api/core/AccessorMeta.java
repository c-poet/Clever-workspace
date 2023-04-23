package cn.cpoet.blog.api.core;

import cn.cpoet.blog.api.annotation.Accessor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 访问目标信息
 *
 * @author CPoet
 */
@Data
@RequiredArgsConstructor
public class AccessorMeta implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 获取特征id
     */
    private final String methodId;

    /**
     * 类上的访问信息
     */
    private final Accessor[] beanAccessor;

    /**
     * 方法上的访问信息
     */
    private final Accessor[] methodAccessor;
}
