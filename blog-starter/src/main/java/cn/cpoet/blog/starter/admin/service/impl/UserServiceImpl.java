package cn.cpoet.blog.starter.admin.service.impl;

import cn.cpoet.blog.repo.repository.UserRepository;
import cn.cpoet.blog.starter.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

}
