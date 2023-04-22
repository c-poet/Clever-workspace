package cn.cpoet.blog.starter.api.common.service.impl;

import cn.cpoet.blog.api.context.AppContextHolder;
import cn.cpoet.blog.auth.constant.AuthSubjectConst;
import cn.cpoet.blog.core.component.UserPassCryptoStrategy;
import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.core.service.LoginLogService;
import cn.cpoet.blog.core.util.RequestUtil;
import cn.cpoet.blog.model.domain.LoginLog;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.repo.repository.UserRepository;
import cn.cpoet.blog.starter.api.common.dto.LoginDTO;
import cn.cpoet.blog.starter.api.common.service.AuthService;
import cn.cpoet.blog.starter.api.common.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

    private final UserRepository userRepository;
    private final LoginLogService loginLogService;
    private final UserPassCryptoStrategy userPassCryptoStrategy;

    @Override
    public Mono<LoginVO> login(LoginDTO loginDTO, ServerWebExchange exchange) {
        return userRepository.findByUsername(loginDTO.getUsername())
            .doOnSuccess(user -> {
                if (user == null || !Boolean.TRUE.equals(user.getEnabled())) {
                    throw new BusException("用户名或者密码错误");
                }
                if (!userPassCryptoStrategy.valid(user, loginDTO.getPassword())) {
                    throw new BusException("用户名或者密码错误");
                }
                if (Boolean.TRUE.equals(user.getLocked())) {
                    throw new BusException("用户已锁定");
                }
            }).flatMap(user -> {
                Map<String, Object> claims = new HashMap<>(1 << 4);
                claims.put(AuthSubjectConst.USER_ID, user.getId());
                claims.put(AuthSubjectConst.NAME, user.getName());
                claims.put(AuthSubjectConst.USER_NAME, user.getUsername());
                claims.put(AuthSubjectConst.GROUP_ID, user.getGroupId());
                String token = AppContextHolder.getAuthContext().authorize(claims);
                LoginVO loginVO = new LoginVO();
                loginVO.setUserId(user.getId());
                loginVO.setToken(token);
                return loginLogService
                    .saveLog(createLog(user, exchange, loginVO))
                    .thenReturn(loginVO);
            });
    }

    private LoginLog createLog(User user, ServerWebExchange exchange, LoginVO loginVO) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getId());
        loginLog.setUsername(user.getUsername());
        loginLog.setIpAddress(RequestUtil.getIpAddr(exchange.getRequest()));
        loginLog.setUserAgent(RequestUtil.getUserAgent(exchange.getRequest()));
        loginLog.setDetail("登录成功，凭证：" + loginVO.getToken());
        loginLog.setLoginTime(LocalDateTime.now());
        return loginLog;
    }
}
