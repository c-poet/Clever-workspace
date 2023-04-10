package cn.cpoet.blog.model;

import cn.cpoet.blog.model.base.BaseRcEntity;
import cn.cpoet.blog.model.constant.PermissionAclType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author CPoet
 */
@Data
@Schema(title = "资源访问控制")
public class PermissionAcl extends BaseRcEntity {

    @Schema(title = "访问对象id")
    private Long itemId;

    @Schema(title = "类型")
    private PermissionAclType type;
}
