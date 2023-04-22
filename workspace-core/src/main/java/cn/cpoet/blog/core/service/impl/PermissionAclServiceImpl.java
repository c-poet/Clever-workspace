package cn.cpoet.blog.core.service.impl;

import cn.cpoet.blog.core.mongo.MongoTemplate;
import cn.cpoet.blog.core.service.PermissionAclService;
import cn.cpoet.blog.model.constant.PermissionAclType;
import cn.cpoet.blog.model.domain.PermissionAcl;
import cn.cpoet.blog.repo.repository.PermissionAclRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionAclServiceImpl implements PermissionAclService {

    private final MongoTemplate mongoTemplate;
    private final PermissionAclRepository permissionAclRepository;

    @Override
    public List<PermissionAcl> listByItem(Long itemId, PermissionAclType type) {
        PermissionAcl permissionAcl = new PermissionAcl();
        permissionAcl.setItemId(itemId);
        permissionAcl.setType(type);
        return permissionAclRepository.findAll(Example.of(permissionAcl));
    }

    @Override
    public List<PermissionAcl> listByPerson(Long userId, Long groupId) {
        List<Criteria> criteriaList = new ArrayList<>(2);
        if (userId != null) {
            PermissionAcl personAcl = new PermissionAcl();
            personAcl.setItemId(userId);
            personAcl.setType(PermissionAclType.PERSON_PERMISSION);
            criteriaList.add(Criteria.byExample(personAcl));
        }
        if (groupId != null) {
            PermissionAcl groupAcl = new PermissionAcl();
            groupAcl.setItemId(groupId);
            groupAcl.setType(PermissionAclType.GROUP_PERMISSION);
            criteriaList.add(Criteria.byExample(groupAcl));
        }
        return CollectionUtils.isEmpty(criteriaList)
            ? Collections.emptyList()
            : mongoTemplate.find(Query.query(new Criteria().orOperator(criteriaList)), PermissionAcl.class);
    }

    @Override
    public void save(Long itemId, PermissionAclType type, List<Long> permissionIds) {
        // 清空所有的授权信息
        if (CollectionUtils.isEmpty(permissionIds)) {
             deleteByItemAndType(itemId, type);
        }
        Set<Long> permissionIdSet = new HashSet<>(permissionIds);
        List<PermissionAcl> permissionAclList = listByItem(itemId, type);
        if (!CollectionUtils.isEmpty(permissionAclList)) {
            Iterator<PermissionAcl> it = permissionAclList.iterator();
            while (it.hasNext()) {
                PermissionAcl next = it.next();
                if (permissionIdSet.contains(next.getPermissionId())) {
                    permissionIdSet.remove(next.getPermissionId());
                    it.remove();
                }
            }
        }
        if (!CollectionUtils.isEmpty(permissionAclList)) {
            permissionAclRepository.deleteAll(permissionAclList);
        }
        if (!CollectionUtils.isEmpty(permissionIdSet)) {
            List<PermissionAcl> newPermissionAclList = genPermissionAcl(itemId, type, permissionIds);
            permissionAclRepository.saveAll(newPermissionAclList);
        }
    }

    @Override
    public void deleteByItemAndType(Long itemId, PermissionAclType type) {
        Criteria criteria = Criteria
            .where(PermissionAcl.Fields.itemId).is(itemId)
            .and(PermissionAcl.Fields.type).is(type);
         mongoTemplate.remove(Query.query(criteria), PermissionAcl.class);
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
