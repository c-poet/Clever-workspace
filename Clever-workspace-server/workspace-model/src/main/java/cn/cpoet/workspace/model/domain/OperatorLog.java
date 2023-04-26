package cn.cpoet.workspace.model.domain;

import cn.cpoet.workspace.model.base.TenantRcEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CPoet
 */
@Data
@Entity
@Schema(title = "操作日志")
@Table(name = "sys_operator_log")
public class OperatorLog extends TenantRcEntity {
    private static final long serialVersionUID = 1L;

}
