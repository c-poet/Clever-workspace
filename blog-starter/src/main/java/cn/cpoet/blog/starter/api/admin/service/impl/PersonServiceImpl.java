package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.core.service.PermissionService;
import cn.cpoet.blog.model.constant.PermissionAclType;
import cn.cpoet.blog.model.constant.PermissionType;
import cn.cpoet.blog.model.domain.PermissionAcl;
import cn.cpoet.blog.starter.api.admin.service.PersonService;
import cn.cpoet.blog.starter.api.admin.vo.MenuNodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PermissionService permissionService;

    @Override
    public Flux<MenuNodeVO> listMenu(Subject subject) {
        return permissionService.listByPerson(subject.getId(), subject.getGroupId(), PermissionType.MENU)
            .map(MenuNodeVO::of)
            .reduceWith(() -> new HashMap<Long, ArrayList<MenuNodeVO>>(1 << 4), (mapper, menu) -> {
                mapper.computeIfAbsent(menu.getParentId(), k -> new ArrayList<>()).add(menu);
                return mapper;
            })
            .flatMapIterable(mapping -> {
                for (ArrayList<MenuNodeVO> children : mapping.values()) {
                    for (MenuNodeVO child : children) {
                        ArrayList<MenuNodeVO> curChildren = mapping.get(child.getId());
                        if (!CollectionUtils.isEmpty(curChildren)) {
                            child.setChildren(curChildren);
                        }
                    }
                }
                return mapping.get(SystemConst.DEFAULT_PID);
            });
    }
}
