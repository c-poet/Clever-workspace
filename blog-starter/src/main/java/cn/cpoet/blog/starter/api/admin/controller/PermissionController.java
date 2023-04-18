package cn.cpoet.blog.starter.api.admin.controller;

import cn.cpoet.blog.api.validation.Insert;
import cn.cpoet.blog.api.validation.Update;
import cn.cpoet.blog.core.dto.IdDTO;
import cn.cpoet.blog.core.dto.IdsDTO;
import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.Permission;
import cn.cpoet.blog.starter.api.admin.param.PermissionParam;
import cn.cpoet.blog.starter.api.admin.service.PermissionService;
import cn.cpoet.blog.starter.api.admin.vo.PermissionNodeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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

    @GetMapping("/getPermissionById")
    @Operation(summary = "根据id查询权限")
    public Mono<Permission> getPermissionById(@RequestParam Long id) {
        return permissionService.getPermissionById(id);
    }

    @GetMapping("/listPermission")
    @Operation(summary = "查询权限列表")
    public Mono<PageVO<Permission>> listPermission(@Validated PermissionParam permissionParam) {
        return permissionService.listPermission(permissionParam);
    }

    @GetMapping("/listPermissionTree")
    @Operation(summary = "查询权限树")
    public Flux<PermissionNodeVO> listPermissionTree() {
        return permissionService.listPermissionTree();
    }

    @PostMapping("/insertPermission")
    @Operation(summary = "新增功能权限")
    public Mono<Permission> insertPermission(@RequestBody @Validated(Insert.class) Permission permission) {
        return permissionService.insertPermission(permission);
    }

    @PostMapping("/updatePermission")
    @Operation(summary = "更新功能权限")
    public Mono<Permission> updatePermission(@RequestBody @Validated(Update.class) Permission permission) {
        return permissionService.updatePermission(permission);
    }

    @PostMapping("/deletePermissionById")
    public Mono<Void> deletePermissionById(@RequestBody @Validated IdDTO idDTO) {
        return permissionService.deletePermissionById(idDTO.getId());
    }

    @PostMapping("/deletePermissionByIds")
    public Mono<Void> deletePermissionByIds(@RequestBody @Validated IdsDTO idsDTO) {
        return permissionService.deletePermissionByIds(idsDTO.getIds());
    }
}
