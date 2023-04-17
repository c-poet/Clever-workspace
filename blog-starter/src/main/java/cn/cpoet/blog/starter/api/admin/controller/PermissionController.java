package cn.cpoet.blog.starter.api.admin.controller;

import cn.cpoet.blog.model.domain.Permission;
import cn.cpoet.blog.starter.api.admin.service.PermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/permission")
@RequiredArgsConstructor
@Tag(name = "权限管理")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping("/save")
    public Mono<Permission> save(@RequestBody @Validated Permission permission) {
        return permissionService.save(permission);
    }
}
