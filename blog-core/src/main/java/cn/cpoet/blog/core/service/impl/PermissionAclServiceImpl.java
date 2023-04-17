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
import reactor.core.publisher.Flux;

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
    public Flux<PermissionAcl> listByItem(Long itemId, PermissionAclType permissionAclType) {
        PermissionAcl permissionAcl = new PermissionAcl();
        permissionAcl.setItemId(itemId);
        permissionAcl.setType(permissionAclType);
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
}
