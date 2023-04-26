package cn.cpoet.workspace.model.domain;

import cn.cpoet.workspace.model.base.TenantEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CPoet
 */
@Data
@Entity
@Schema(title = "租户")
@Table(name = "sys_tenantry")
public class Tenantry extends TenantEntity {
    @Schema(title = "租户编码")
    private String code;

    @Schema(title = "租户名称")
    private String name;

    @Schema(title = "租户介绍")
    private String description;
}
