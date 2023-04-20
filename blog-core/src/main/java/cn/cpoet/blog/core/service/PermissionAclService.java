package cn.cpoet.blog.core.service;

import cn.cpoet.blog.model.constant.PermissionAclType;
import cn.cpoet.blog.model.domain.PermissionAcl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    Flux<PermissionAcl> listByItem(Long itemId, PermissionAclType type);

    /**
     * 查询用户acl列表
     *
     * @param userId  用户id
     * @param groupId 用户组id
     * @return acl列表
     */
    Flux<PermissionAcl> listByPerson(Long userId, Long groupId);

    /**
     * 保存控制权限列表
     *
     * @param itemId        目标对象id
     * @param type          目标对象类型
     * @param permissionIds 目标权限id集合
     * @return 操作结果
     */
    Mono<Void> save(Long itemId, PermissionAclType type, List<Long> permissionIds);

    /**
     * 移出授权控制列表
     *
     * @param itemId 目标对象id
     * @param type   目标对象类型
     * @return 操作结果
     */
    Mono<Void> deleteByItemAndType(Long itemId, PermissionAclType type);
}
