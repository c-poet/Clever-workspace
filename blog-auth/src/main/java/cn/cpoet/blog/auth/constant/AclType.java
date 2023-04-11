package cn.cpoet.blog.auth.constant;

import cn.cpoet.blog.api.annotation.EnumAppear;
import cn.cpoet.blog.api.annotation.EnumId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 访问控制类型
 *
 * @author CPoet
 */
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum AclType {
    /**
     * 角色类型
     */
    ROLE(1, "role", "角色"),

    /**
     * 资源类型
     */
    PERMISSION(2, "permission", "资源");

    @EnumId
    private final int id;

    @EnumAppear
    private final String code;

    @EnumAppear
    private final String desc;
}
