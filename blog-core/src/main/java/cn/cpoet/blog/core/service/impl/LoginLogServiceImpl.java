package cn.cpoet.blog.core.service.impl;

import cn.cpoet.blog.core.service.LoginLogService;
import cn.cpoet.blog.repo.repository.LoginLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl implements LoginLogService {

    private final LoginLogRepository loginLogRepository;


}
