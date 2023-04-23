package cn.cpoet.workspace.starter.admin.service;

import cn.cpoet.workspace.core.component.UserPassCryptoStrategy;
import cn.cpoet.workspace.core.util.UUIDUtil;
import cn.cpoet.workspace.model.domain.User;
import cn.cpoet.workspace.repo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author CPoet
 */
@Slf4j
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserPassCryptoStrategy userPassCryptoStrategy;

    @Test
    public void initUser() {
        User user = new User();
        user.setName("CPoet");
        user.setUsername("cpoet");
        user.setNickName("编程小白");
        user.setEnabled(Boolean.TRUE);
        user.setBuildIn(Boolean.TRUE);
        user.setSalt(UUIDUtil.random());
        user.setPassword(userPassCryptoStrategy.encode(user, "12345678"));
        userRepository.insert(user);
    }
}