package cn.cpoet.blog.model.constant;

import cn.cpoet.blog.api.annotation.EnumId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author CPoet
 */
@Getter
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
    private final String code;
    private final String desc;
}
