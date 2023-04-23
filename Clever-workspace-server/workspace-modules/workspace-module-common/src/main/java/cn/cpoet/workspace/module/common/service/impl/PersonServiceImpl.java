package cn.cpoet.workspace.module.common.service.impl;

import cn.cpoet.workspace.api.context.Subject;
import cn.cpoet.workspace.api.exception.BusException;
import cn.cpoet.workspace.model.domain.User;
import cn.cpoet.workspace.mapper.UserMapper;
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

    private final UserMapper userMapper;

    @Override
    public User getPersonInfo(Subject subject) {
        User user = userMapper.selectByPrimaryKey(subject.getId());
        if (user == null) {
            throw new BusException("用户状态错误，请重新登录");
        }
        return user;
    }
}
