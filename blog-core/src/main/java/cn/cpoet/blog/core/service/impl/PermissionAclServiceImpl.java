package cn.cpoet.blog.core.service.impl;

import cn.cpoet.blog.core.service.PermissionAclService;
import cn.cpoet.blog.model.constant.PermissionAclType;
import cn.cpoet.blog.model.domain.PermissionAcl;
import cn.cpoet.blog.repo.repository.PermissionAclRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionAclServiceImpl implements PermissionAclService {

    private final ReactiveMongoTemplate mongoTemplate;
    private final PermissionAclRepository permissionAclRepository;

    @Override
    public Flux<PermissionAcl> listByItem(Long itemId, PermissionAclType type) {
        PermissionAcl permissionAcl = new PermissionAcl();
        permissionAcl.setItemId(itemId);
        permissionAcl.setType(type);
        return permissionAclRepository.findAll(Example.of(permissionAcl));
    }

    @Override
    public Flux<PermissionAcl> listByPerson(Long userId, Long groupId) {
        Criteria criteria = null;
        if (userId != null) {
            PermissionAcl personAcl = new PermissionAcl();
            personAcl.setItemId(userId);
            personAcl.setType(PermissionAclType.PERSON_PERMISSION);
            criteria = Criteria.byExample(personAcl);
        }
        if (groupId != null) {
            PermissionAcl groupAcl = new PermissionAcl();
            groupAcl.setItemId(groupId);
            groupAcl.setType(PermissionAclType.GROUP_PERMISSION);
            criteria = criteria == null ? Criteria.byExample(groupAcl) : criteria.orOperator(Criteria.byExample(groupAcl));
        }
        return criteria == null ? Flux.empty() : mongoTemplate.find(Query.query(criteria), PermissionAcl.class);
    }

    @Override
    public Mono<Void> save(Long itemId, PermissionAclType type, List<Long> permissionIds) {
        // 清空所有的授权信息
        if (CollectionUtils.isEmpty(permissionIds)) {
            return deleteByItemAndType(itemId, type);
        }
        return listByItem(itemId, type)
            .collectList()
            .flatMap(permissionAclList -> {
                if (CollectionUtils.isEmpty(permissionAclList)) {
                    return Mono.just(permissionIds);
                }
                Iterator<PermissionAcl> it = permissionAclList.iterator();
                Set<Long> aclSet = new HashSet<>(permissionIds);
                while (it.hasNext()) {
                    PermissionAcl next = it.next();
                    if (aclSet.contains(next.getPermissionId())) {
                        aclSet.remove(next.getPermissionId());
                        it.remove();
                    }
                }
                if (CollectionUtils.isEmpty(permissionAclList)) {
                    return Mono.just(aclSet);
                }
                return permissionAclRepository
                    .deleteAll(permissionAclList)
                    .map(item -> aclSet);
            }).map(addSet -> genPermissionAcl(itemId, type, addSet))
            .flatMapMany(permissionAclRepository::insert)
            .ignoreElements()
            .flatMap(item -> Mono.empty());
    }

    @Override
    public Mono<Void> deleteByItemAndType(Long itemId, PermissionAclType type) {
        Criteria criteria = Criteria
            .where(PermissionAcl.Fields.itemId).is(itemId)
            .and(PermissionAcl.Fields.type).is(type);
        return mongoTemplate.remove(Query.query(criteria), PermissionAcl.class)
            .flatMap(result -> Mono.empty());
    }

    private PermissionAcl genPermissionAcl(Long itemId, PermissionAclType type, Long permissionId) {
        PermissionAcl permissionAcl = new PermissionAcl();
        permissionAcl.setItemId(itemId);
        permissionAcl.setType(type);
        permissionAcl.setPermissionId(permissionId);
        return permissionAcl;
    }

    private List<PermissionAcl> genPermissionAcl(Long itemId, PermissionAclType type, Collection<Long> permissionIds) {
        return permissionIds
            .stream()
            .map(permissionId -> genPermissionAcl(itemId, type, permissionId))
            .collect(Collectors.toList());
    }
}
