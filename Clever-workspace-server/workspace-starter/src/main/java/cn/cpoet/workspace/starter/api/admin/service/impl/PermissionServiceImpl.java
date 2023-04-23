package cn.cpoet.workspace.starter.api.admin.service.impl;

import cn.cpoet.workspace.api.constant.SystemConst;
import cn.cpoet.workspace.core.exception.BusException;
import cn.cpoet.workspace.core.mongo.MongoTemplate;
import cn.cpoet.workspace.core.util.BeanUtil;
import cn.cpoet.workspace.core.vo.PageVO;
import cn.cpoet.workspace.model.domain.Permission;
import cn.cpoet.workspace.repo.repository.PermissionRepository;
import cn.cpoet.workspace.starter.api.admin.param.PermissionParam;
import cn.cpoet.workspace.starter.api.admin.service.PermissionService;
import cn.cpoet.workspace.starter.api.admin.vo.PermissionNodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final MongoTemplate mongoTemplate;
    private final PermissionRepository permissionRepository;
    private final cn.cpoet.workspace.core.service.PermissionService permissionService;

    @Override
    public Permission getPermissionById(Long id) {
        return permissionRepository.findById(id).orElse(null);
    }

    @Override
    public PageVO<Permission> listPermission(PermissionParam param) {
        return mongoTemplate.findPageParam(param, Permission.class);
    }

    @Override
    public List<PermissionNodeVO> listPermissionTree(PermissionParam param) {
        List<Permission> permissions = mongoTemplate.findParam(param, Permission.class, Sort.by(Permission.Fields.order));
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptyList();
        }
        Map<Long, List<PermissionNodeVO>> mapping = new HashMap<>(1 << 4);
        for (Permission permission : permissions) {
            mapping.computeIfAbsent(permission.getParentId(), k -> new ArrayList<>()).add(PermissionNodeVO.of(permission));
        }
        for (List<PermissionNodeVO> children : mapping.values()) {
            for (PermissionNodeVO child : children) {
                List<PermissionNodeVO> curChildren = mapping.get(child.getId());
                if (!CollectionUtils.isEmpty(curChildren)) {
                    child.setChildren(curChildren);
                }
            }
        }
        return mapping.getOrDefault(SystemConst.DEFAULT_PID, Collections.emptyList());
    }

    @Override
    public Permission insertPermission(Permission permission) {
        permission.setBuildIn(Boolean.FALSE);
        return permissionRepository.insert(permission);
    }

    @Override
    public Permission updatePermission(Permission permission) {
        Permission old = permissionRepository.findById(permission.getId()).orElseThrow(() -> new BusException("功能不存在"));
        return permissionRepository.save(BeanUtil.copyEditableProperties(permission, old));
    }

    @Override
    public void deletePermissionById(Long id) {
        permissionRepository.findById(id)
            .ifPresent(permission -> {
                checkDeletePermission(permission);
                permissionRepository.delete(permission);
            });
    }

    @Override
    public void deletePermissionByIds(List<Long> ids) {
        List<Permission> permissions = permissionRepository.findAllById(ids);
        if (!CollectionUtils.isEmpty(permissions)) {
            for (Permission permission : permissions) {
                checkDeletePermission(permission);
            }
            permissionRepository.deleteAll(permissions);
        }
    }

    private void checkDeletePermission(Permission permission) {
        if (Boolean.TRUE.equals(permission.getBuildIn())) {
            throw new BusException("内置权限不允许删除");
        }
    }
}
