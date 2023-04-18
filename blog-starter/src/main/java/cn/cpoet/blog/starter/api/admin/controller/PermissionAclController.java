package cn.cpoet.blog.starter.api.admin.controller;

import cn.cpoet.blog.starter.api.admin.service.PermissionAclService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CPoet
 */
@RestController
@RequestMapping("/permissionAcl")
@RequiredArgsConstructor
public class PermissionAclController {
    private final PermissionAclService permissionAclService;
}
