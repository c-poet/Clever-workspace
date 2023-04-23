package cn.cpoet.blog.core.service.impl;

import cn.cpoet.blog.core.mongo.MongoTemplate;
import cn.cpoet.blog.core.service.UserService;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.repo.repository.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public boolean existsByGroups(List<Long> groupIds) {
        Criteria criteria = Criteria.where(User.Fields.groupId).in(groupIds);
        return mongoTemplate.exists(Query.query(criteria), User.class);
    }
}
