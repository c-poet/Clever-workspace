package cn.cpoet.blog.starter.api.common.service.impl;

import cn.cpoet.blog.api.context.AppContextHolder;
import cn.cpoet.blog.auth.constant.AuthSubjectConst;
import cn.cpoet.blog.core.component.UserPassCryptoStrategy;
import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.repo.repository.UserRepository;
import cn.cpoet.blog.starter.api.common.dto.LoginDTO;
import cn.cpoet.blog.starter.api.common.service.AuthService;
import cn.cpoet.blog.starter.api.common.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    private final UserPassCryptoStrategy userPassCryptoStrategy;

    @Override
    public Mono<LoginVO> login(LoginDTO loginDTO) {
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
            }).map(user -> {
                Map<String, Object> claims = new HashMap<>(1 << 4);
                claims.put(AuthSubjectConst.USER_ID, user.getId());
                claims.put(AuthSubjectConst.NAME, user.getName());
                claims.put(AuthSubjectConst.USER_NAME, user.getUsername());
                claims.put(AuthSubjectConst.GROUP_ID, user.getGroupId());
                String token = AppContextHolder.getAuthContext().authorize(claims);
                LoginVO loginVO = new LoginVO();
                loginVO.setUserId(user.getId());
                loginVO.setToken(token);
                return loginVO;
            });
    }
}
