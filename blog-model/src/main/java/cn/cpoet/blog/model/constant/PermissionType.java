package cn.cpoet.blog.model.constant;

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
    NONE("none", 1, "未定义"),

    /**
     * 操作
     */
    OPERATOR("operator", 2, "操作"),

    /**
     * 资源
     */
    PERMISSION("permission", 3, "资源"),

    /**
     * 菜单
     */
    MENU("menu", 4, "菜单");

    private final String code;
    private final Integer value;
    private final String label;
}
