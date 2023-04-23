package cn.cpoet.workspace.module.common.service.impl;

import cn.cpoet.workspace.api.context.Subject;
import cn.cpoet.workspace.core.exception.BusException;
import cn.cpoet.workspace.model.domain.User;
import cn.cpoet.workspace.repo.repository.UserRepository;
import cn.cpoet.workspace.module.common.service.PersonService;
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
