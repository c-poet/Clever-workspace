package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.model.constant.PermissionAclType;
import cn.cpoet.blog.model.domain.PermissionAcl;
import cn.cpoet.blog.repo.repository.PermissionAclRepository;
import cn.cpoet.blog.repo.repository.PermissionRepository;
import cn.cpoet.blog.starter.api.admin.service.PersonService;
import cn.cpoet.blog.starter.api.admin.vo.MenuNodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
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

    private final PermissionRepository permissionRepository;
    private final PermissionAclRepository permissionAclRepository;

    @Override
    public Flux<MenuNodeVO> listMenu(Subject subject) {
        PermissionAcl permissionAcl = new PermissionAcl();
        permissionAcl.setItemId(subject.getId());
        permissionAcl.setType(PermissionAclType.PERSON_PERMISSION);
        return permissionAclRepository.findAll(Example.of(permissionAcl))
            .map(PermissionAcl::getPermissionId)
            .transform(permissionRepository::findAllById)
            .map(MenuNodeVO::of)
            .reduceWith(() -> new HashMap<Long, ArrayList<MenuNodeVO>>(1 << 4), (mapper, menu) -> {
                mapper.computeIfAbsent(menu.getParentId(), k -> new ArrayList<>()).add(menu);
                return mapper;
            })
            .flatMapIterable(mapper -> {
                for (ArrayList<MenuNodeVO> children : mapper.values()) {
                    for (MenuNodeVO child : children) {
                        ArrayList<MenuNodeVO> curChildren = mapper.get(child.getId());
                        if (!CollectionUtils.isEmpty(curChildren)) {
                            child.setChildren(curChildren);
                        }
                    }
                }
                return mapper.get(SystemConst.DEFAULT_PID);
            });
    }
}
