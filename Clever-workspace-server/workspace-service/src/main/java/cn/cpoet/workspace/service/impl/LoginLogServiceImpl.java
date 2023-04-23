package cn.cpoet.workspace.service.impl;

import cn.cpoet.workspace.mapper.LoginLogMapper;
import cn.cpoet.workspace.model.domain.LoginLog;
import cn.cpoet.workspace.service.LoginLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLog> implements LoginLogService {

    private final LoginLogMapper loginLogMapper;

    @Async
    @Override
    public void saveLog(LoginLog loginLog) {
        loginLogMapper.insert(loginLog);
    }
}
