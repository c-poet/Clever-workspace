package cn.cpoet.blog.starter.common.controller;

import cn.cpoet.blog.starter.common.dto.LoginDTO;
import cn.cpoet.blog.starter.common.service.AuthService;
import cn.cpoet.blog.starter.common.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<LoginVO> login(@RequestBody @Validated LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}
