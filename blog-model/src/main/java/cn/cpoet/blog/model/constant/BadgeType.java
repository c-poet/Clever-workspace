package cn.cpoet.blog.model.constant;

import cn.cpoet.blog.api.annotation.EnumId;
import cn.cpoet.blog.api.core.Dict;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author CPoet
 */
@Getter
@RequiredArgsConstructor
public enum BadgeType implements Dict {
    /**
     * 无
     */
    NONE("01", "none", "无"),

    /**
     * 圆点
     */
    DOT("02", "dot", "圆点"),

    /**
     * new
     */
    NEW("03", "new", "New"),

    /**
     * 数字n
     */
    NUMBER("04", "number", "数字");

    @EnumId
    private final String value;
    private final String code;
    private final String label;
}
