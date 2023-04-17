package cn.cpoet.blog.starter.api.admin.service;

import cn.cpoet.blog.model.domain.Permission;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
public interface PermissionService {
    /**
     * 保存权限信息
     *
     * @param permission 权限
     * @return 权限信息
     */
    Mono<Permission> save(Permission permission);
}
