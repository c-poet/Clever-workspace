package cn.cpoet.workspace.core.service.impl;

import cn.cpoet.workspace.core.mongo.MongoTemplate;
import cn.cpoet.workspace.core.service.UserService;
import cn.cpoet.workspace.model.domain.User;
import cn.cpoet.workspace.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MongoTemplate mongoTemplate;
    private final UserMapper userRepository;

    @Override
    public boolean existsByGroups(List<Long> groupIds) {
        Criteria criteria = Criteria.where(User.Fields.groupId).in(groupIds);
        return mongoTemplate.exists(Query.query(criteria), User.class);
    }
}
