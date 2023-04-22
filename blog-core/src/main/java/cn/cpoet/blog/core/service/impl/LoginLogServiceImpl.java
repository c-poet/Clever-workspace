package cn.cpoet.blog.core.service.impl;

import cn.cpoet.blog.core.mongo.MongoTemplate;
import cn.cpoet.blog.core.service.LoginLogService;
import cn.cpoet.blog.model.base.BaseRcEntity;
import cn.cpoet.blog.model.domain.LoginLog;
import cn.cpoet.blog.repo.repository.LoginLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<LoginLog> saveLog(LoginLog loginLog) {
        return loginLogRepository.insert(loginLog);
    }

    @Override
    public Mono<LoginLog> findLastLog(Long userId) {
        Criteria criteria = Criteria.where(LoginLog.Fields.userId).is(userId);
        Query query = Query.query(criteria).with(Sort.by(BaseRcEntity.Fields.createTime)).limit(1);
        return mongoTemplate.findOne(query, LoginLog.class);
    }

    @Override
    public Flux<LoginLog> findLastLog(List<Long> userIds) {
        return Flux.concat(userIds.stream().map(this::findLastLog).collect(Collectors.toList()));
    }
}
