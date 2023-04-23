package cn.cpoet.workspace.module.admin.controller;

import cn.cpoet.workspace.model.constant.PermissionAclType;
import cn.cpoet.workspace.module.admin.dto.PermissionAclDTO;
import cn.cpoet.workspace.module.admin.service.PermissionAclService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author CPoet
 */
@RestController
@RequestMapping("/permissionAcl")
@RequiredArgsConstructor
@Tag(name = "访问控制权限")
public class PermissionAclController {

    private final PermissionAclService permissionAclService;

    @GetMapping("/listPermissionId")
    @Operation(summary = "查询授权权限id列表")
    public List<Long> listPermissionId(@RequestParam Long itemId, @RequestParam PermissionAclType type) {
        return permissionAclService.listPermissionId(itemId, type);
    }

    @PostMapping("/savePermissionAcl")
    @Operation(summary = "保存授权权限")
    public void savePermissionAcl(@RequestBody @Validated PermissionAclDTO permissionAclDTO) {
         permissionAclService.savePermissionAcl(permissionAclDTO);
    }
}
