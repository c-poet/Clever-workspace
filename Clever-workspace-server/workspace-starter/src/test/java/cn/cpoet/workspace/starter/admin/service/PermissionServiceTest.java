package cn.cpoet.workspace.starter.admin.service;

import cn.cpoet.workspace.api.constant.SystemConst;
import cn.cpoet.workspace.core.util.CamelUtil;
import cn.cpoet.workspace.model.constant.PermissionAclType;
import cn.cpoet.workspace.model.constant.PermissionType;
import cn.cpoet.workspace.model.domain.Permission;
import cn.cpoet.workspace.model.domain.PermissionAcl;
import cn.cpoet.workspace.model.domain.User;
import cn.cpoet.workspace.repo.repository.PermissionAclRepository;
import cn.cpoet.workspace.repo.repository.PermissionRepository;
import cn.cpoet.workspace.repo.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
        savePermissions(SystemConst.DEFAULT_PID, routers);
    }

    private void savePermissions(Long parentId, List<OriginRoute> routers) {
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
                permission.setBuildIn(Boolean.TRUE);
                permission.setDescription("来自admin模板");
                permission.setEnabled(Boolean.TRUE);
                permissionRepository.save(permission);
                savePermissions(permission.getId(), router.getChildren());
            }
        }
    }

    @Test
    public void initPermissionAcl() {
        User user = userRepository.findByUsername("cpoet");
        List<Permission> permissions = permissionRepository.findAll();
        List<PermissionAcl> permissionAclList = new ArrayList<>(permissions.size());
        for (Permission permission : permissions) {
            PermissionAcl permissionAcl = new PermissionAcl();
            permissionAcl.setItemId(user.getId());
            permissionAcl.setPermissionId(permission.getId());
            permissionAcl.setType(PermissionAclType.PERSON_PERMISSION);
            permissionAclList.add(permissionAcl);
        }
        permissionAclRepository.saveAll(permissionAclList);
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