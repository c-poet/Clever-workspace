package cn.cpoet.blog.api.core;

import lombok.Data;

import java.io.Serializable;

/**
 * 访问目标信息
 *
 * @author CPoet
 */
@Data
public class AccessorMeta implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 获取特征id
     */
    private String id;
}
