package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.core.mongo.MongoTemplate;
import cn.cpoet.blog.core.util.EditableUtil;
import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.Permission;
import cn.cpoet.blog.repo.repository.PermissionRepository;
import cn.cpoet.blog.starter.api.admin.param.PermissionParam;
import cn.cpoet.blog.starter.api.admin.service.PermissionService;
import cn.cpoet.blog.starter.api.admin.vo.PermissionNodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
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

    @Override
    public Mono<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    @Override
    public Mono<PageVO<Permission>> listPermission(PermissionParam permissionParam) {
        Criteria criteria = new Criteria();
        if (StringUtils.hasText(permissionParam.getName())) {
            criteria = criteria.and(Permission.Fields.name).is(permissionParam.getName());
        }
        if (StringUtils.hasText(permissionParam.getCode())) {
            criteria = criteria.and(Permission.Fields.code).is(permissionParam.getCode());
        }
        return mongoTemplate.find(Query.query(criteria), permissionParam, Permission.class);
    }

    @Override
    public Flux<PermissionNodeVO> listPermissionTree() {
        return permissionRepository.findAll()
            .map(PermissionNodeVO::of)
            .reduceWith(() -> new HashMap<Long, ArrayList<PermissionNodeVO>>(1 << 4), (mapper, permission) -> {
                mapper.computeIfAbsent(permission.getParentId(), k -> new ArrayList<>()).add(permission);
                return mapper;
            })
            .flatMapIterable(mapping -> {
                for (ArrayList<PermissionNodeVO> children : mapping.values()) {
                    for (PermissionNodeVO child : children) {
                        ArrayList<PermissionNodeVO> curChildren = mapping.get(child.getId());
                        if (!CollectionUtils.isEmpty(curChildren)) {
                            child.setChildren(curChildren);
                        }
                    }
                }
                return mapping.get(SystemConst.DEFAULT_PID);
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
            .doOnSuccess(old -> BeanUtils.copyProperties(permission, old, EditableUtil.getIgnoreFieldByObj(old)))
            .flatMap(permissionRepository::save);
    }

    @Override
    public Mono<Void> deletePermissionById(Long id) {
        return permissionRepository
            .findById(id)
            .doOnSuccess(permission -> {
                if (Boolean.TRUE.equals(permission.getBuildIn())) {
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
