package cn.cpoet.blog.model.constant;

import cn.cpoet.blog.api.annotation.EnumId;
import cn.cpoet.blog.api.core.Dict;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 权限类型
 *
 * @author CPoet
 */
@Getter
@RequiredArgsConstructor
public enum PermissionType implements Dict {
    /**
     * 未定义
     */
    NONE("01", "none", "未定义"),

    /**
     * 操作
     */
    OPERATOR("02", "operator", "操作"),

    /**
     * 资源
     */
    PERMISSION("03", "permission", "资源"),

    /**
     * 菜单
     */
    MENU("04", "menu", "菜单");

    @EnumId
    private final String value;
    private final String code;
    private final String label;
}
