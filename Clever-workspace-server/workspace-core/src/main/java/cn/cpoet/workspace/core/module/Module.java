package cn.cpoet.workspace.core.module;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * 模块信息
 *
 * @author CPoet
 */
@Data
@Setter(AccessLevel.PACKAGE)
public class Module {
    /**
     * 父级模块
     */
    private Module parent;

    /**
     * 所在限定路径
     */
    private String path;

    /**
     * 模块名
     */
    private String name;

    /**
     * 介绍
     */
    private String desc;

    /**
     * 重写信息
     */
    private Rewrite rewrite;

    /**
     * 路径绑定信息
     */
    private Mapping mapping;
}
