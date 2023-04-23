package cn.cpoet.workspace.module.admin.service.impl;

import cn.cpoet.workspace.model.constant.PermissionAclType;
import cn.cpoet.workspace.module.admin.dto.PermissionAclDTO;
import cn.cpoet.workspace.module.admin.service.PermissionAclService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionAclServiceImpl implements PermissionAclService {

    @Override
    public List<Long> listPermissionId(Long userId, PermissionAclType type) {
//        return permissionAclService
//            .listByItem(userId, type)
//            .stream()
//            .map(PermissionAcl::getPermissionId)
//            .collect(Collectors.toList());
        return null;
    }

    @Override
    public void savePermissionAcl(PermissionAclDTO permissionAclDTO) {
//         permissionAclService.save(permissionAclDTO.getItemId(), permissionAclDTO.getType(),
//             permissionAclDTO.getPermissionIds());
        return;
    }
}
