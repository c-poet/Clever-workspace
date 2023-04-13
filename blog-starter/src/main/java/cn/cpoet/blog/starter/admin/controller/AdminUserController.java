package cn.cpoet.blog.starter.admin.controller;

import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.starter.admin.service.AdminUserService;
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
@RequestMapping("/admin/user")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping("/save")
    public Mono<User> save(@RequestBody @Validated User user, Subject subject) {
        return adminUserService.save(subject, user);
    }

    @PostMapping("/batchDeleteById")
    public Mono<Void> deleteById(@RequestBody @Validated List<Long> ids, Subject subject) {
        return adminUserService.deleteById(subject, ids);
    }
}
