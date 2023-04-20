package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.model.constant.PermissionAclType;
import cn.cpoet.blog.model.domain.PermissionAcl;
import cn.cpoet.blog.starter.api.admin.dto.PermissionAclDTO;
import cn.cpoet.blog.starter.api.admin.service.PermissionAclService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionAclServiceImpl implements PermissionAclService {

    private final cn.cpoet.blog.core.service.PermissionAclService permissionAclService;

    @Override
    public Flux<Long> listPermissionId(Long userId, PermissionAclType type) {
        return permissionAclService
            .listByItem(userId, type)
            .map(PermissionAcl::getPermissionId);
    }

    @Override
    public Mono<Void> savePermissionAcl(PermissionAclDTO permissionAclDTO) {
        return permissionAclService.save(permissionAclDTO.getItemId(), permissionAclDTO.getType(),
            permissionAclDTO.getPermissionIds());
    }
}
