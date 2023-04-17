package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.model.domain.Permission;
import cn.cpoet.blog.repo.repository.PermissionRepository;
import cn.cpoet.blog.starter.api.admin.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Mono<Permission> save(Permission permission) {
        return permissionRepository.save(permission);
    }
}
