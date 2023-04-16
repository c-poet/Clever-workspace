package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.repo.repository.PermissionRepository;
import cn.cpoet.blog.starter.api.admin.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private PermissionRepository permissionRepository;


}
