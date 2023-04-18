package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.core.mongo.MongoTemplate;
import cn.cpoet.blog.core.service.UserService;
import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.Group;
import cn.cpoet.blog.repo.repository.GroupRepository;
import cn.cpoet.blog.starter.api.admin.param.GroupParam;
import cn.cpoet.blog.starter.api.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final UserService userService;
    private final MongoTemplate mongoTemplate;
    private final GroupRepository groupRepository;

    @Override
    public Mono<Group> getGroupById(Long groupId) {
        return groupRepository.findById(groupId);
    }

    @Override
    public Mono<PageVO<Group>> listGroup(GroupParam groupParam) {
        Criteria criteria = new Criteria();
        return mongoTemplate.find(Query.query(criteria), groupParam, Group.class);
    }

    @Override
    public Mono<Group> insertGroup(Group group) {
        return groupRepository.insert(group);
    }

    @Override
    public Mono<Group> updateGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Mono<Void> deleteGroupById(Long id) {
        return userService
            .existsByGroups(Collections.singletonList(id))
            .flatMap(existsGroup -> {
                if (Boolean.TRUE.equals(existsGroup)) {
                    return Mono.error(new BusException("分组下存在用户，拒绝删除"));
                }
                return groupRepository.deleteById(id);
            });
    }

    @Override
    public Mono<Void> deleteGroupByIds(List<Long> ids) {
        return userService
            .existsByGroups(ids)
            .flatMap(existsGroup -> {
                if (Boolean.TRUE.equals(existsGroup)) {
                    return Mono.error(new BusException("分组下存在用户，拒绝删除"));
                }
                return groupRepository.deleteAllById(ids);
            });
    }
}
