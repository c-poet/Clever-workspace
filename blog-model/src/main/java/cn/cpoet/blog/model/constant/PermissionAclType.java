package cn.cpoet.blog.model.constant;

import cn.cpoet.blog.api.annotation.EnumAppear;
import cn.cpoet.blog.api.annotation.EnumId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 资源访问控制类型
 *
 * @author CPoet
 */
@Getter
@Accessors(fluent = true)
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
    @EnumAppear
    private final String desc;
}
