package cn.cpoet.blog.starter.admin.service;

import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.core.util.CamelUtil;
import cn.cpoet.blog.model.constant.PermissionAclType;
import cn.cpoet.blog.model.constant.PermissionType;
import cn.cpoet.blog.model.domain.Permission;
import cn.cpoet.blog.model.domain.PermissionAcl;
import cn.cpoet.blog.repo.repository.PermissionAclRepository;
import cn.cpoet.blog.repo.repository.PermissionRepository;
import cn.cpoet.blog.repo.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PermissionServiceTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionAclRepository permissionAclRepository;

    @Test
    public void initPermission() throws IOException {
        List<OriginRoute> routers = objectMapper.readValue(this.getClass().getResourceAsStream("/admin-routers.json"),
            new TypeReference<List<OriginRoute>>() {
            });
        savePermissions(SystemConst.DEFAULT_PID, routers).blockFirst();
    }

    private Flux<Permission> savePermissions(Long parentId, List<OriginRoute> routers) {
        Flux<Permission> flux = Flux.empty();
        if (!CollectionUtils.isEmpty(routers)) {
            for (OriginRoute router : routers) {
                Permission permission = new Permission();
                permission.setParentId(parentId);
                String nameUrl = StringUtils.hasText(router.getMenuUrl()) ? router.getMenuUrl() : router.getOutLink();
                if (StringUtils.hasText(nameUrl)) {
                    int nameIndex = nameUrl.lastIndexOf("/");
                    permission.setCode(CamelUtil.underline2Camel(nameIndex != -1 ? nameUrl.substring(nameIndex + 1) : nameUrl));
                } else {
                    permission.setCode(router.getMenuName());
                }
                permission.setName(router.getMenuName());
                permission.setIcon(router.getIcon());
                permission.setPath(router.getMenuUrl());
                permission.setUrl(router.getOutLink());
                permission.setBadge(router.getTip());
                permission.setIsSingle(router.getIsSingle());
                permission.setHidden(router.getHidden());
                permission.setAffix(router.getAffix());
                permission.setCacheable(router.getCacheable());
                permission.setType(PermissionType.MENU);
                permission.setDescription("来自admin模板");
                permission.setEnabled(Boolean.TRUE);
                flux = flux.concatWith(permissionRepository
                    .save(permission)
                    .flatMapMany(per -> savePermissions(per.getId(), router.getChildren())));
            }
        }
        return flux;
    }

    @Test
    public void initPermissionAcl() {
        userRepository
            .findByUsername("cpoet")
            .flatMapMany(user -> permissionRepository
                .findAll()
                .flatMap(permission -> {
                    PermissionAcl permissionAcl = new PermissionAcl();
                    permissionAcl.setItemId(user.getId());
                    permissionAcl.setPermissionId(permission.getId());
                    permissionAcl.setType(PermissionAclType.PERSON_PERMISSION);
                    return permissionAclRepository.save(permissionAcl);
                })).blockLast();

    }

    @Test
    public void exportPermission() throws IOException {
        String path = "/Users/cpoet/OpenSource/Clever-blog/blog-starter/src/test/resources/export-permission.json";
        List<Permission> permissions = new ArrayList<>();
        permissionRepository
            .findAll()
            .map(permissions::add)
            .blockLast();
        objectMapper.writeValue(new File(path), permissions);
    }

    @Data
    public static class OriginRoute {
        private String menuUrl;
        private String menuName;
        private Boolean hidden;
        private String outLink;
        private Boolean affix;
        private Boolean cacheable;
        private String icon;
        private String tip;
        private Boolean isSingle;
        private List<OriginRoute> children;
    }
}