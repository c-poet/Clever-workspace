package cn.cpoet.blog.model.constant;

import cn.cpoet.blog.api.annotation.EnumId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 资源访问控制类型
 *
 * @author CPoet
 */
@Getter
@RequiredArgsConstructor
public enum PermissionAclType {
    /**
     * 用户组权限
     */
    GROUP_PERMISSION(1, "用户组权限"),

    /**
     * 用户权限
     */
    PERSON_PERMISSION(2, "用户权限");

    @EnumId
    private final int id;
    private final String desc;
}
