package cn.cpoet.workspace.model.domain;

import cn.cpoet.workspace.model.base.TenantRcEntity;
import cn.cpoet.workspace.model.constant.PermissionAclType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author CPoet
 */
@Data
@Entity
@Schema(title = "资源访问控制")
@Table(name = "sys_permission_acl")
public class PermissionAcl extends TenantRcEntity {
    private static final long serialVersionUID = 1L;

    @Schema(title = "访问对象id")
    @NotNull(message = "授权对象的id不能为空")
    private Long itemId;

    @Schema(title = "权限id")
    @NotNull(message = "权限id不能为空")
    private Long permissionId;

    @Schema(title = "类型")
    @NotNull(message = "授权对象类型不能为空")
    private PermissionAclType type;
}
