package cn.cpoet.workspace.core.service;

import cn.cpoet.workspace.model.constant.PermissionAclType;
import cn.cpoet.workspace.model.domain.PermissionAcl;

import java.util.List;

/**
 * @author CPoet
 */
public interface PermissionAclService {
    /**
     * 查询acl列表
     *
     * @param itemId 实体项id
     * @param type   实体类型
     * @return 列表
     */
    List<PermissionAcl> listByItem(Long itemId, PermissionAclType type);

    /**
     * 查询用户acl列表
     *
     * @param userId  用户id
     * @param groupId 用户组id
     * @return acl列表
     */
    List<PermissionAcl> listByPerson(Long userId, Long groupId);

    /**
     * 保存控制权限列表
     *
     * @param itemId        目标对象id
     * @param type          目标对象类型
     * @param permissionIds 目标权限id集合
     */
    void save(Long itemId, PermissionAclType type, List<Long> permissionIds);

    /**
     * 移出授权控制列表
     *
     * @param itemId 目标对象id
     * @param type   目标对象类型
     */
    void deleteByItemAndType(Long itemId, PermissionAclType type);
}
