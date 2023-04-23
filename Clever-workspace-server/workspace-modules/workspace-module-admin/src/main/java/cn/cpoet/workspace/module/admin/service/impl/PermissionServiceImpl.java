package cn.cpoet.workspace.module.admin.service.impl;

import cn.cpoet.workspace.api.exception.BusException;
import cn.cpoet.workspace.core.vo.PageVO;
import cn.cpoet.workspace.model.domain.Permission;
import cn.cpoet.workspace.module.admin.param.PermissionParam;
import cn.cpoet.workspace.module.admin.service.PermissionService;
import cn.cpoet.workspace.module.admin.vo.PermissionNodeVO;
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
public class PermissionServiceImpl implements PermissionService {


    @Override
    public Permission getPermissionById(Long id) {
        return null;
    }

    @Override
    public PageVO<Permission> listPermission(PermissionParam param) {
        return null;
    }

    @Override
    public List<PermissionNodeVO> listPermissionTree(PermissionParam param) {
//        List<Permission> permissions = mongoTemplate.findParam(param, Permission.class, Sort.by(Permission.Fields.order));
//        if (CollectionUtils.isEmpty(permissions)) {
//            return Collections.emptyList();
//        }
//        Map<Long, List<PermissionNodeVO>> mapping = new HashMap<>(1 << 4);
//        for (Permission permission : permissions) {
//            mapping.computeIfAbsent(permission.getParentId(), k -> new ArrayList<>()).add(PermissionNodeVO.of(permission));
//        }
//        for (List<PermissionNodeVO> children : mapping.values()) {
//            for (PermissionNodeVO child : children) {
//                List<PermissionNodeVO> curChildren = mapping.get(child.getId());
//                if (!CollectionUtils.isEmpty(curChildren)) {
//                    child.setChildren(curChildren);
//                }
//            }
//        }
//        return mapping.getOrDefault(SystemConst.DEFAULT_PID, Collections.emptyList());
        return null;
    }

    @Override
    public Permission insertPermission(Permission permission) {
//        permission.setBuildIn(Boolean.FALSE);
//        return permissionRepository.insert(permission);
        return null;
    }

    @Override
    public Permission updatePermission(Permission permission) {
//        Permission old = permissionRepository.findById(permission.getId()).orElseThrow(() -> new BusException("功能不存在"));
//        return permissionRepository.save(BeanUtil.copyEditableProperties(permission, old));
        return null;
    }

    @Override
    public void deletePermissionById(Long id) {
//        permissionRepository.findById(id)
//            .ifPresent(permission -> {
//                checkDeletePermission(permission);
//                permissionRepository.delete(permission);
//            });
        return;
    }

    @Override
    public void deletePermissionByIds(List<Long> ids) {
//        List<Permission> permissions = permissionRepository.findAllById(ids);
//        if (!CollectionUtils.isEmpty(permissions)) {
//            for (Permission permission : permissions) {
//                checkDeletePermission(permission);
//            }
//            permissionRepository.deleteAll(permissions);
//        }
        return;
    }

    private void checkDeletePermission(Permission permission) {
        if (Boolean.TRUE.equals(permission.getBuildIn())) {
            throw new BusException("内置权限不允许删除");
        }
    }
}
