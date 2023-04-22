package cn.cpoet.blog.starter.api.common.controller;

import cn.cpoet.blog.starter.api.common.dto.LoginDTO;
import cn.cpoet.blog.starter.api.common.service.AuthService;
import cn.cpoet.blog.starter.api.common.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CPoet
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginVO login(@RequestBody @Validated LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }
}