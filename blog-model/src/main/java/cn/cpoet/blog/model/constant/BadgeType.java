package cn.cpoet.blog.model.constant;

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
    NONE("none", 1, "无"),

    /**
     * 圆点
     */
    DOT("dot", 2, "圆点"),

    /**
     * new
     */
    NEW("new", 3, "New"),

    /**
     * 数字n
     */
    NUMBER("number", 4, "数字");

    private final String code;
    private final Integer value;
    private final String label;
}
