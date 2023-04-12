package cn.cpoet.blog.starter.service.impl;

import cn.cpoet.blog.repo.repository.UserRepository;
import cn.cpoet.blog.starter.dto.LoginDTO;
import cn.cpoet.blog.starter.service.AuthService;
import cn.cpoet.blog.starter.vo.TokenVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public Mono<TokenVO> login(LoginDTO loginDTO) {
        userRepository.findByUsername(loginDTO.getUsername());
        return Mono.empty();
    }
}
