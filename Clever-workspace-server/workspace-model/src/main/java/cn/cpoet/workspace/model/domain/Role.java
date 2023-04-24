package cn.cpoet.workspace.model.domain;

import cn.cpoet.workspace.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CPoet
 */
@Data
@Entity
@Schema(title = "角色")
@Table(name = "sys_role")
public class Role extends BaseEntity {
    @Schema(title = "角色编码")
    @Column(name = "code")
    private String code;

    @Schema(title = "角色名称")
    @Column(name = "name")
    private String name;

    @Schema(title = "角色描述")
    @Column(name = "description")
    private String description;

    @Schema(title = "是否启用")
    @Column(name = "enabled")
    private Boolean enabled;
}
