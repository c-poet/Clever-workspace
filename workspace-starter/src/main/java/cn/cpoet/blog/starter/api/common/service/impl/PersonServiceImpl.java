package cn.cpoet.blog.starter.api.common.service.impl;

import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.repo.repository.UserRepository;
import cn.cpoet.blog.starter.api.common.service.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final UserRepository userRepository;

    @Override
    public User getPersonInfo(Subject subject) {
        return userRepository.findById(subject.getId()).orElseThrow(() -> new BusException("用户状态错误，请重新登录"));
    }
}
