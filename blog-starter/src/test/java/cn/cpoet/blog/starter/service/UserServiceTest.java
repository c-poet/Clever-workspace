package cn.cpoet.blog.starter.service;

import cn.cpoet.blog.api.core.IdGenerator;
import cn.cpoet.blog.core.component.UserPassCryptoStrategy;
import cn.cpoet.blog.core.util.UUIDUtil;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.repo.repository.UserRepository;
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
    private IdGenerator<Long> idGenerator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPassCryptoStrategy userPassCryptoStrategy;

    @Test
    public void initUser() {
        User user = new User();
        user.setId(idGenerator.nextId());
        user.setName("CPoet");
        user.setUsername("cpoet");
        user.setEnabled(Boolean.TRUE);
        user.setSalt(UUIDUtil.random());
        user.setPassword(userPassCryptoStrategy.encode(user, "12345678"));
        userRepository.insert(user).subscribe(System.out::println);
    }

    @Test
    public void updateUser() {
        userRepository.findByUsername("cpoet")
            .flatMap(user -> {
                user.setEnabled(Boolean.TRUE);
                return userRepository.save(user);
            }).subscribe();
    }
}