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
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Permission> listByPerson(Long userId, Long groupId, PermissionType permissionType) {
        List<PermissionAcl> permissionAclList = permissionAclService.listByPerson(userId, groupId);
        if (CollectionUtils.isEmpty(permissionAclList)) {
            return Collections.emptyList();
        }
        List<Long> permissionIds = permissionAclList.stream().map(PermissionAcl::getPermissionId).collect(Collectors.toList());
        Criteria criteria = Criteria.where(BaseRcEntity.Fields.id).in(permissionIds)
            .and(Permission.Fields.enabled).is(Boolean.TRUE);
        if (permissionType != null) {
            criteria = criteria.and(Permission.Fields.type).is(permissionType);
        }
        Query query = Query.query(criteria).with(Sort.by(Permission.Fields.order));
        return mongoTemplate.find(query, Permission.class);
    }

    @Override
    public List<String> listCodeByPerson(Long userId, Long groupId, PermissionType permissionType) {
        return listByPerson(userId, groupId, permissionType)
            .stream()
            .map(Permission::getCode)
            .collect(Collectors.toList());
    }
}
