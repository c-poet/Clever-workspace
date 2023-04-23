package cn.cpoet.workspace.core.service.impl;

import cn.cpoet.workspace.core.mongo.MongoTemplate;
import cn.cpoet.workspace.core.service.LoginLogService;
import cn.cpoet.workspace.model.base.BaseRcEntity;
import cn.cpoet.workspace.model.domain.LoginLog;
import cn.cpoet.workspace.repo.repository.LoginLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl implements LoginLogService {

    private final MongoTemplate mongoTemplate;
    private final LoginLogRepository loginLogRepository;

    @Override
    public LoginLog saveLog(LoginLog loginLog) {
        return loginLogRepository.insert(loginLog);
    }

    @Override
    public LoginLog findLastLog(Long userId) {
        Criteria criteria = Criteria.where(LoginLog.Fields.userId).is(userId);
        Query query = Query.query(criteria).with(Sort.by(BaseRcEntity.Fields.createTime)).limit(1);
        return mongoTemplate.findOne(query, LoginLog.class);
    }

    @Override
    public List<LoginLog> findLastLog(List<Long> userIds) {
        return userIds.stream().map(this::findLastLog).collect(Collectors.toList());
    }
}
