package cn.cpoet.blog.model.constant;

import cn.cpoet.blog.api.annotation.EnumId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 权限类型
 *
 * @author CPoet
 */
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum PermissionType {
    /**
     * 未定义
     */
    NONE(1, "none", "未定义"),

    /**
     * 操作
     */
    OPERATOR(2, "operator", "操作"),

    /**
     * 资源
     */
    PERMISSION(3, "permission", "资源"),

    /**
     * 菜单
     */
    MENU(4, "menu", "菜单");

    @EnumId
    private final int id;
    private final String code;
    private final String desc;
}
