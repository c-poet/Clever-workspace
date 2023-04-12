package cn.cpoet.blog.starter.controller;

import cn.cpoet.blog.starter.dto.LoginDTO;
import cn.cpoet.blog.starter.service.AuthService;
import cn.cpoet.blog.starter.vo.TokenVO;
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
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Mono<TokenVO> login(@RequestBody @Validated LoginDTO loginDTO) {
        return authService.login(loginDTO)
            .doOnSuccess(System.out::println)
            .doOnError(Throwable::printStackTrace);
    }
}
