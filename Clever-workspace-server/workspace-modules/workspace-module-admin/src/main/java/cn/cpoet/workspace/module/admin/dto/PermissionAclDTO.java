package cn.cpoet.workspace.module.admin.dto;

import cn.cpoet.workspace.model.constant.PermissionAclType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author CPoet
 */
@Data
@Schema(title = "访问控制列表")
public class PermissionAclDTO {
    @Schema(title = "目标类型")
    @NotNull(message = "授权目标不能为空")
    private Long itemId;

    @Schema(title = "控制类型")
    @NotNull(message = "权限控制类型不能为空")
    private PermissionAclType type;

    @Schema(title = "权限列表")
    private List<Long> permissionIds;
}
