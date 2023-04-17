package cn.cpoet.blog.core.service;

import cn.cpoet.blog.model.constant.PermissionAclType;
import cn.cpoet.blog.model.domain.PermissionAcl;
import reactor.core.publisher.Flux;

/**
 * @author CPoet
 */
public interface PermissionAclService {
    /**
     * 查询acl列表
     *
     * @param itemId            实体项id
     * @param permissionAclType 实体类型
     * @return 列表
     */
    Flux<PermissionAcl> listByItem(Long itemId, PermissionAclType permissionAclType);

    /**
     * 查询用户acl列表
     *
     * @param userId  用户id
     * @param groupId 用户组id
     * @return acl列表
     */
    Flux<PermissionAcl> listByPerson(Long userId, Long groupId);
}
