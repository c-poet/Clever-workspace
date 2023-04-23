package cn.cpoet.workspace.starter.api.admin.service;

import cn.cpoet.workspace.model.constant.PermissionAclType;
import cn.cpoet.workspace.starter.api.admin.dto.PermissionAclDTO;

import java.util.List;

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
    List<Long> listPermissionId(Long itemId, PermissionAclType type);

    /**
     * 保存授权
     *
     * @param permissionAclDTO 授权信息
     */
    void savePermissionAcl(PermissionAclDTO permissionAclDTO);
}
