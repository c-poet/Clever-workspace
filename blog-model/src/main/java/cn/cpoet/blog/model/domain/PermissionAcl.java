package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.model.base.BaseRcEntity;
import cn.cpoet.blog.model.constant.PermissionAclType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CPoet
 */
@Data
@Schema(title = "资源访问控制")
@Document("blog_permission_acl")
public class PermissionAcl extends BaseRcEntity {

    @Schema(title = "访问对象id")
    private Long itemId;

    @Schema(title = "权限id")
    private Long permissionId;

    @Schema(title = "类型")
    private PermissionAclType type;
}
