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
@EnumDict(value = "Sex")
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum SexEnum {
    /**
     * 未知
     */
    NONE(0, "none", "未知"),

    /**
     * 男性
     */
    MALE(1, "male", "男性"),

    /**
     * 女性
     */
    FEMALE(2, "female", "女性"),

    /**
     * 其他
     */
    OTHER(3, "other", "其他");

    @EnumId
    private final int id;
    @EnumAppear
    private final String code;
    @EnumAppear
    private final String desc;
}
