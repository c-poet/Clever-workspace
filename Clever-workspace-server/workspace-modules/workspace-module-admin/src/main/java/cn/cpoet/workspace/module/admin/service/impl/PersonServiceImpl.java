package cn.cpoet.workspace.module.admin.service.impl;

import cn.cpoet.workspace.api.constant.SystemConst;
import cn.cpoet.workspace.api.context.Subject;
import cn.cpoet.workspace.core.service.PermissionService;
import cn.cpoet.workspace.model.constant.PermissionType;
import cn.cpoet.workspace.model.domain.Permission;
import cn.cpoet.workspace.module.admin.service.PersonService;
import cn.cpoet.workspace.module.admin.vo.MenuNodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PermissionService permissionService;

    @Override
    public List<MenuNodeVO> listMenu(Subject subject) {
        List<Permission> permissions = permissionService.listByPerson(subject.getId(), subject.getGroupId(), PermissionType.MENU);
        if (CollectionUtils.isEmpty(permissions)) {
            return Collections.emptyList();
        }
        Map<Long, List<MenuNodeVO>> mapping = new HashMap<>(1 << 4);
        for (Permission permission : permissions) {
            mapping.computeIfAbsent(permission.getParentId(), k -> new ArrayList<>()).add(MenuNodeVO.of(permission));
        }
        for (List<MenuNodeVO> children : mapping.values()) {
            for (MenuNodeVO child : children) {
                List<MenuNodeVO> curChildren = mapping.get(child.getId());
                if (!CollectionUtils.isEmpty(curChildren)) {
                    child.setChildren(curChildren);
                }
            }
        }
        return mapping.getOrDefault(SystemConst.DEFAULT_PID, Collections.emptyList());
    }
}
