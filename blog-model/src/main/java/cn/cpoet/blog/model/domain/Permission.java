package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.model.base.BaseEntity;
import cn.cpoet.blog.model.constant.BadgeType;
import cn.cpoet.blog.model.constant.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

/**
 * @author CPoet
 */
@Data
@Schema(title = "权限/资源")
@Document("blog_permission")
@FieldNameConstants
public class Permission extends BaseEntity {

    @Schema(title = "父级id")
    private Long parentId;

    @Indexed(unique = true)
    @Schema(title = "权限编码")
    @NotEmpty(message = "权限编码不能为空")
    private String code;

    @Schema(title = "权限名称")
    @NotEmpty(message = "权限名称不能为空")
    private String name;

    @Schema(title = "图标")
    private String icon;

    @Schema(title = "访问路径")
    private String path;

    @Schema(title = "访问url，用于外部系统")
    private String url;

    @Schema(title = "徽标类型")
    private BadgeType badgeType;

    @Schema(title = "徽标")
    private String badge;

    @Schema(title = "isSingle")
    private Boolean isSingle;

    @Schema(title = "是否隐藏")
    private Boolean hidden;

    @Schema(title = "是否固定")
    private Boolean affix;

    @Schema(title = "是否缓存")
    private Boolean cacheable;

    @Schema(title = "类型")
    private PermissionType type;

    @Schema(title = "排序值")
    private Integer order;

    @Schema(title = "权限描述")
    private String description;

    @Schema(title = "是否启用")
    private Boolean enabled;
}
