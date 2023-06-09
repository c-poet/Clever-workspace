package cn.cpoet.workspace.model.constant;

import cn.cpoet.workspace.api.annotation.EnumAppear;
import cn.cpoet.workspace.api.annotation.EnumDict;
import cn.cpoet.workspace.api.annotation.EnumId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author CPoet
 */
@Getter
@EnumDict
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum BadgeType {
    /**
     * 无
     */
    NONE(1, "none", "无"),

    /**
     * 圆点
     */
    DOT(2, "dot", "圆点"),

    /**
     * new
     */
    NEW(3, "new", "New"),

    /**
     * 数字n
     */
    NUMBER(4, "number", "数字");

    @EnumId
    private final int id;
    @EnumAppear
    private final String code;
    @EnumAppear
    private final String desc;
}