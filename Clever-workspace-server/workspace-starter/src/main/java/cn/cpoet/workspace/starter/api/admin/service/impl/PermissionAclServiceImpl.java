package cn.cpoet.workspace.starter.api.admin.service.impl;

import cn.cpoet.workspace.model.constant.PermissionAclType;
import cn.cpoet.workspace.model.domain.PermissionAcl;
import cn.cpoet.workspace.starter.api.admin.dto.PermissionAclDTO;
import cn.cpoet.workspace.starter.api.admin.service.PermissionAclService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionAclServiceImpl implements PermissionAclService {

    private final cn.cpoet.workspace.core.service.PermissionAclService permissionAclService;

    @Override
    public List<Long> listPermissionId(Long userId, PermissionAclType type) {
        return permissionAclService
            .listByItem(userId, type)
            .stream()
            .map(PermissionAcl::getPermissionId)
            .collect(Collectors.toList());
    }

    @Override
    public void savePermissionAcl(PermissionAclDTO permissionAclDTO) {
         permissionAclService.save(permissionAclDTO.getItemId(), permissionAclDTO.getType(),
             permissionAclDTO.getPermissionIds());
    }
}
