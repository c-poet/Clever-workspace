package cn.cpoet.blog.starter.api.admin.controller;

import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.starter.api.admin.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author CPoet
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public Mono<User> save(@RequestBody @Validated User user, Subject subject) {
        return userService.save(subject, user);
    }

    @PostMapping("/batchDeleteById")
    public Mono<Void> deleteById(@RequestBody @Validated List<Long> ids, Subject subject) {
        return userService.deleteById(subject, ids);
    }
}
