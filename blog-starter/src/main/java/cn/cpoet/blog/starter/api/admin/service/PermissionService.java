package cn.cpoet.blog.starter.api.admin.service;

import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.Permission;
import cn.cpoet.blog.starter.api.admin.param.PermissionParam;
import cn.cpoet.blog.starter.api.admin.vo.PermissionNodeVO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author CPoet
 */
public interface PermissionService {
    /**
     * 查询id查询权限信息
     *
     * @param id 权限id
     * @return 权限信息
     */
    Mono<Permission> getPermissionById(Long id);

    /**
     * 分页查询权限列表
     *
     * @param permissionParam 查询参数
     * @return 权限列表
     */
    Mono<PageVO<Permission>> listPermission(PermissionParam permissionParam);

    /**
     * 查询权限树形
     *
     * @return 权限树形
     */
    Flux<PermissionNodeVO> listPermissionTree();

    /**
     * 新增权限信息
     *
     * @param permission 权限
     * @return 权限信息
     */
    Mono<Permission> insertPermission(Permission permission);

    /**
     * 更新权限信息
     *
     * @param permission 权限
     * @return 权限信息
     */
    Mono<Permission> updatePermission(Permission permission);

    /**
     * 根据id删除权限信息
     *
     * @param id 权限id
     * @return void
     */
    Mono<Void> deletePermissionById(Long id);

    /**
     * 批量删除权限信息
     *
     * @param ids id列表
     * @return void
     */
    Mono<Void> deletePermissionByIds(List<Long> ids);
}
