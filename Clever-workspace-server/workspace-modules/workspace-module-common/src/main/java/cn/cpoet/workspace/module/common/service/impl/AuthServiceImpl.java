package cn.cpoet.workspace.module.common.service.impl;

import cn.cpoet.workspace.api.context.AppContextHolder;
import cn.cpoet.workspace.auth.constant.AuthSubjectConst;
import cn.cpoet.workspace.core.component.UserPassCryptoStrategy;
import cn.cpoet.workspace.core.exception.BusException;
import cn.cpoet.workspace.core.service.LoginLogService;
import cn.cpoet.workspace.core.util.RequestUtil;
import cn.cpoet.workspace.model.domain.LoginLog;
import cn.cpoet.workspace.model.domain.User;
import cn.cpoet.workspace.mapper.UserMapper;
import cn.cpoet.workspace.module.common.dto.LoginDTO;
import cn.cpoet.workspace.module.common.service.AuthService;
import cn.cpoet.workspace.module.common.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final HttpServletRequest request;
    private final UserMapper userRepository;
    private final LoginLogService loginLogService;
    private final UserPassCryptoStrategy userPassCryptoStrategy;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user == null || !Boolean.TRUE.equals(user.getEnabled())) {
            throw new BusException("用户名或者密码错误");
        }
        if (!userPassCryptoStrategy.valid(user, loginDTO.getPassword())) {
            throw new BusException("用户名或者密码错误");
        }
        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new BusException("用户已锁定");
        }
        Map<String, Object> claims = new HashMap<>(1 << 4);
        claims.put(AuthSubjectConst.USER_ID, user.getId());
        claims.put(AuthSubjectConst.NAME, user.getName());
        claims.put(AuthSubjectConst.USER_NAME, user.getUsername());
        claims.put(AuthSubjectConst.GROUP_ID, user.getGroupId());
        String token = AppContextHolder.getAuthContext().authorize(claims);
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setToken(token);
        loginLogService.saveLog(createLog(user, loginVO));
        return loginVO;
    }

    private LoginLog createLog(User user, LoginVO loginVO) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getId());
        loginLog.setUsername(user.getUsername());
        loginLog.setIpAddress(RequestUtil.getIpAddress(request));
        loginLog.setUserAgent(RequestUtil.getUserAgent(request));
        loginLog.setDetail("登录成功，凭证：" + loginVO.getToken());
        loginLog.setLoginTime(LocalDateTime.now());
        return loginLog;
    }
}
