package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.core.component.UserPassCryptoStrategy;
import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.core.mongo.MongoTemplate;
import cn.cpoet.blog.core.util.UUIDUtil;
import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.repo.repository.UserRepository;
import cn.cpoet.blog.starter.api.admin.dto.UserDTO;
import cn.cpoet.blog.starter.api.admin.param.UserParam;
import cn.cpoet.blog.starter.api.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    private final UserPassCryptoStrategy userPassCryptoStrategy;

    @Override
    public Mono<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Mono<PageVO<User>> listUser(UserParam userParam) {
        Criteria criteria = new Criteria();
        return mongoTemplate.find(Query.query(criteria), userParam, User.class);
    }

    @Override
    public Mono<User> insertUser(UserDTO userDTO) {
        userDTO.setSalt(UUIDUtil.random());
        userDTO.setPassword(userPassCryptoStrategy.encode(userDTO, userDTO.getUserPass()));
        userDTO.setBuildIn(Boolean.FALSE);
        return userRepository.insert(userDTO);
    }

    @Override
    public Mono<User> updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<Void> deleteUserById(Long id) {
        return userRepository
            .findById(id)
            .doOnSuccess(user -> {
                if (Boolean.TRUE.equals(user.getBuildIn())) {
                    throw new BusException("内置用户不允许删除");
                }
            })
            .flatMap(userRepository::delete);
    }

    @Override
    public Mono<Void> deleteUserByIds(List<Long> ids) {
        return userRepository
            .findAllById(ids)
            .collectList()
            .doOnSuccess(users -> {
                for (User user : users) {
                    if (Boolean.TRUE.equals(user.getBuildIn())) {
                        throw new BusException("内置用户不允许删除");
                    }
                }
            })
            .flatMap(userRepository::deleteAll);
    }
}
