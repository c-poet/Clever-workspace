package cn.cpoet.workspace.core.service;

import cn.cpoet.workspace.model.constant.PermissionType;
import cn.cpoet.workspace.model.domain.Permission;

import java.util.List;

/**
 * @author CPoet
 */
public interface PermissionService {
    /**
     * 获取用户权限列表
     *
     * @param userId         用户id
     * @param groupId        用户组id
     * @param permissionType 权限类型，不指定为全部
     * @return 用户权限列表
     * @
     */
    List<Permission> listByPerson(Long userId, Long groupId, PermissionType permissionType);

    /**
     * 获取用户权限编码列表
     *
     * @param userId         用户id
     * @param groupId        用户组id
     * @param permissionType 权限类型，不指定为全部
     * @return 用户权限编码列表
     */
    List<String> listCodeByPerson(Long userId, Long groupId, PermissionType permissionType);
}
