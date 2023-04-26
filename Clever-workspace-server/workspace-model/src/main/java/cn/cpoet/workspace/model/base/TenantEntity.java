package cn.cpoet.workspace.model.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author CPoet
 */
@Data
@MappedSuperclass
@Schema(title = "租户实体")
public abstract class TenantEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Column(name = "tenant_id")
    @Schema(title = "租户id")
    private Long tenantId;
}
