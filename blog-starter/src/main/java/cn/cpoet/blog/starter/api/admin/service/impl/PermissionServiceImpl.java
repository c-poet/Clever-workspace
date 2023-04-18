package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.core.mongo.MongoTemplate;
import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.Permission;
import cn.cpoet.blog.repo.repository.PermissionRepository;
import cn.cpoet.blog.starter.api.admin.param.PermissionParam;
import cn.cpoet.blog.starter.api.admin.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

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
    @Transactional(rollbackFor = Exception.class)
    public Mono<Permission> insertPermission(Permission permission) {
        permission.setBuildIn(Boolean.FALSE);
        return permissionRepository.save(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Permission> updatePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> deletePermissionById(Long id) {
        return permissionRepository
            .findById(id)
            .doOnSuccess(permission -> {
                if (permission == null) {
                    throw new BusException("删除的数据不存在");
                }
                if (Boolean.TRUE.equals(permission.getBuildIn())) {
                    throw new BusException("系统内置数据不允许删除");
                }
            })
            .flatMap(permissionRepository::delete);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<Void> batchDeletePermission(List<Long> ids) {
        return permissionRepository
            .findAllById(ids)
            .collectList()
            .doOnSuccess(permissions -> {
                if (permissions.size() != ids.size()) {
                    throw new BusException("删除的数据不存在");
                }
                for (Permission permission : permissions) {
                    if (Boolean.TRUE.equals(permission.getBuildIn())) {
                        throw new BusException("系统内置数据不允许删除");
                    }
                }
            }).flatMap(permissionRepository::deleteAll);
    }
}
