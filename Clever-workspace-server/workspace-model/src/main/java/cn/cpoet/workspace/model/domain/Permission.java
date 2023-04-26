package cn.cpoet.workspace.model.domain;

import cn.cpoet.workspace.api.annotation.Editable;
import cn.cpoet.workspace.api.validation.Insert;
import cn.cpoet.workspace.api.validation.Update;
import cn.cpoet.workspace.model.base.TenantEntity;
import cn.cpoet.workspace.model.constant.BadgeType;
import cn.cpoet.workspace.model.constant.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author CPoet
 */
@Data
@Entity
@Editable
@Schema(title = "权限/资源")
@Table(name = "sys_permission")
public class Permission extends TenantEntity {
    private static final long serialVersionUID = 1L;

    @Schema(title = "父级id")
    @NotNull(message = "父级不能为空", groups = {Insert.class, Update.class})
    @Column(name = "parent_id")
    private Long parentId;

    @Schema(title = "权限编码")
    @NotEmpty(message = "权限编码不能为空", groups = {Insert.class, Update.class})
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "功能编码由数字、字母及下划线组成")
    @Column(name = "code")
    private String code;

    @Schema(title = "权限名称")
    @NotEmpty(message = "权限名称不能为空", groups = {Insert.class, Update.class})
    @Column(name = "name")
    private String name;

    @Schema(title = "图标")
    @Column(name = "icon")
    private String icon;

    @Schema(title = "访问路径")
    @Column(name = "path")
    private String path;

    @Schema(title = "访问url，用于外部系统")
    @Column(name = "url")
    private String url;

    @Schema(title = "徽标类型")
    @NotNull(message = "徽标类型不能为空", groups = {Insert.class, Update.class})
    @Column(name = "badge_type")
    private BadgeType badgeType;

    @Schema(title = "徽标")
    @Column(name = "badge")
    private String badge;

    @Schema(title = "isSingle")
    @Column(name = "is_single")
    private Boolean isSingle;

    @Schema(title = "是否隐藏")
    @Column(name = "hidden")
    private Boolean hidden;

    @Schema(title = "是否固定")
    @Column(name = "affix")
    private Boolean affix;

    @Schema(title = "是否缓存")
    @Column(name = "cacheable")
    private Boolean cacheable;

    @Schema(title = "类型")
    @NotNull(message = "权限类型不能为空", groups = {Insert.class, Update.class})
    @Column(name = "type")
    private PermissionType type;

    @Schema(title = "排序值")
    @NotNull(message = "排序值不能为空", groups = {Insert.class, Update.class})
    @Column(name = "order")
    private Integer order;

    @Schema(title = "权限描述")
    @Column(name = "description")
    private String description;

    @Schema(title = "是否内置")
    @Editable(value = false)
    @Column(name = "build_in")
    private Boolean buildIn;

    @Schema(title = "是否启用")
    @Column(name = "enabled")
    private Boolean enabled;
}
