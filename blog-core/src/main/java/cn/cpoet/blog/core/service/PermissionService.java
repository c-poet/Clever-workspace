package cn.cpoet.blog.core.service;

import cn.cpoet.blog.model.constant.PermissionType;
import cn.cpoet.blog.model.domain.Permission;
import reactor.core.publisher.Flux;

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
    Flux<Permission> listByPerson(Long userId, Long groupId, PermissionType permissionType);

    /**
     * 获取用户权限编码列表
     *
     * @param userId         用户id
     * @param groupId        用户组id
     * @param permissionType 权限类型，不指定为全部
     * @return 用户权限编码列表
     */
    Flux<String> listCodeByPerson(Long userId, Long groupId, PermissionType permissionType);
}
