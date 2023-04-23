package cn.cpoet.workspace.starter.api.admin.service;

import cn.cpoet.workspace.core.vo.PageVO;
import cn.cpoet.workspace.model.domain.Permission;
import cn.cpoet.workspace.starter.api.admin.param.PermissionParam;
import cn.cpoet.workspace.starter.api.admin.vo.PermissionNodeVO;

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
    Permission getPermissionById(Long id);

    /**
     * 分页查询权限列表
     *
     * @param param 查询参数
     * @return 权限列表
     */
    PageVO<Permission> listPermission(PermissionParam param);

    /**
     * 查询权限树形
     *
     * @param param 查询参数
     * @return 权限树形
     */
    List<PermissionNodeVO> listPermissionTree(PermissionParam param);

    /**
     * 新增权限信息
     *
     * @param permission 权限
     * @return 权限信息
     */
    Permission insertPermission(Permission permission);

    /**
     * 更新权限信息
     *
     * @param permission 权限
     * @return 权限信息
     */
    Permission updatePermission(Permission permission);

    /**
     * 根据id删除权限信息
     *
     * @param id 权限id
     */
    void deletePermissionById(Long id);

    /**
     * 批量删除权限信息
     *
     * @param ids id列表
     */
    void deletePermissionByIds(List<Long> ids);
}
