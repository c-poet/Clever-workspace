package cn.cpoet.blog.core.service.impl;

import cn.cpoet.blog.core.mongo.MongoTemplate;
import cn.cpoet.blog.core.service.PermissionAclService;
import cn.cpoet.blog.core.service.PermissionService;
import cn.cpoet.blog.model.base.BaseRcEntity;
import cn.cpoet.blog.model.constant.PermissionType;
import cn.cpoet.blog.model.domain.Permission;
import cn.cpoet.blog.model.domain.PermissionAcl;
import cn.cpoet.blog.repo.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final MongoTemplate mongoTemplate;
    private final PermissionRepository permissionRepository;
    private final PermissionAclService permissionAclService;

    @Override
    public Flux<Permission> listByPerson(Long userId, Long groupId, PermissionType permissionType) {
        return permissionAclService
            .listByPerson(userId, groupId)
            .map(PermissionAcl::getPermissionId)
            .collectList()
            .map(ids -> {
                Criteria criteria = Criteria.where(BaseRcEntity.Fields.id).in(ids)
                    .and(Permission.Fields.enabled).is(Boolean.TRUE);
                if (permissionType != null) {
                    criteria = criteria.and(Permission.Fields.type).is(permissionType);
                }
                return Query.query(criteria).with(Sort.by(Permission.Fields.order));
            }).flatMapMany(query -> mongoTemplate.find(query, Permission.class));
    }

    @Override
    public Flux<String> listCodeByPerson(Long userId, Long groupId, PermissionType permissionType) {
        return listByPerson(userId, groupId, permissionType).map(Permission::getCode);
    }
}
