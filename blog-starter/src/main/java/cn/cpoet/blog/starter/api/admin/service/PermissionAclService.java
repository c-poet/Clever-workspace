package cn.cpoet.blog.starter.api.admin.service;

import cn.cpoet.blog.model.constant.PermissionAclType;
import cn.cpoet.blog.starter.api.admin.dto.PermissionAclDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
public interface PermissionAclService {
    /**
     * 获取用户权限id列表
     *
     * @param itemId 授权对象id
     * @param type   授权对象类型
     * @return 权限id列表
     */
    Flux<Long> listPermissionId(Long itemId, PermissionAclType type);

    /**
     * 保存授权
     *
     * @param permissionAclDTO 授权信息
     * @return 操作结果
     */
    Mono<Void> savePermissionAcl(PermissionAclDTO permissionAclDTO);
}
