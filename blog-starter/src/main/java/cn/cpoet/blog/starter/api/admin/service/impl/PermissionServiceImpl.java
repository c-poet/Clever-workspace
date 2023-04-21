package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.core.mongo.MongoTemplate;
import cn.cpoet.blog.core.util.BeanUtil;
import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.Permission;
import cn.cpoet.blog.repo.repository.PermissionRepository;
import cn.cpoet.blog.starter.api.admin.param.PermissionParam;
import cn.cpoet.blog.starter.api.admin.service.PermissionService;
import cn.cpoet.blog.starter.api.admin.vo.PermissionNodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final MongoTemplate mongoTemplate;
    private final PermissionRepository permissionRepository;
    private final cn.cpoet.blog.core.service.PermissionService permissionService;

    @Override
    public Mono<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Mono<PageVO<Permission>> listPermission(PermissionParam param) {
        return mongoTemplate.findPageParam(param, Permission.class);
    }

    @Override
    public Flux<PermissionNodeVO> listPermissionTree(PermissionParam param) {
        return mongoTemplate
            .findParam(param, Permission.class, Sort.by(Permission.Fields.order))
            .map(PermissionNodeVO::of)
            .reduceWith(() -> new HashMap<Long, List<PermissionNodeVO>>(1 << 4), (mapper, permission) -> {
                mapper.computeIfAbsent(permission.getParentId(), k -> new ArrayList<>()).add(permission);
                return mapper;
            })
            .flatMapIterable(mapping -> {
                for (List<PermissionNodeVO> children : mapping.values()) {
                    for (PermissionNodeVO child : children) {
                        List<PermissionNodeVO> curChildren = mapping.get(child.getId());
                        if (!CollectionUtils.isEmpty(curChildren)) {
                            child.setChildren(curChildren);
                        }
                    }
                }
                return mapping.getOrDefault(SystemConst.DEFAULT_PID, Collections.emptyList());
            });
    }

    @Override
    public Mono<Permission> insertPermission(Permission permission) {
        permission.setBuildIn(Boolean.FALSE);
        return permissionRepository.insert(permission);
    }

    @Override
    public Mono<Permission> updatePermission(Permission permission) {
        return permissionRepository
            .findById(permission.getId())
            .doOnSuccess(old -> BeanUtil.copyEditableProperties(permission, old))
            .flatMap(permissionRepository::save);
    }

    @Override
    public Mono<Void> deletePermissionById(Long id) {
        return permissionRepository
            .findById(id)
            .doOnSuccess(permission -> {
                if (permission != null && Boolean.TRUE.equals(permission.getBuildIn())) {
                    throw new BusException("内置权限不允许删除");
                }
            })
            .flatMap(permissionRepository::delete);
    }

    @Override
    public Mono<Void> deletePermissionByIds(List<Long> ids) {
        return permissionRepository
            .findAllById(ids)
            .collectList()
            .doOnSuccess(permissions -> {
                for (Permission permission : permissions) {
                    if (Boolean.TRUE.equals(permission.getBuildIn())) {
                        throw new BusException("内置权限不允许删除");
                    }
                }
            })
            .flatMap(permissionRepository::deleteAll);
    }
}
