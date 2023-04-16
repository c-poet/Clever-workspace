package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.core.component.UserPassCryptoStrategy;
import cn.cpoet.blog.core.util.UUIDUtil;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.repo.repository.UserRepository;
import cn.cpoet.blog.starter.api.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UserRepository userRepository;
    private final UserPassCryptoStrategy userPassCryptoStrategy;

    @Override
    public Mono<User> save(Subject subject, User user) {
        if (user.getId() == null) {
            user.setSalt(UUIDUtil.random());
            user.setPassword(userPassCryptoStrategy.encode(user, user.getPassword()));
        }
        if (user.getGroupId() == null) {
            user.setGroupId(SystemConst.DEFAULT_ID);
        }
        return userRepository.save(user);
    }

    @Override
    public Mono<Void> deleteById(Subject subject, List<Long> ids) {
        return userRepository.deleteAllById(ids);
    }
}
