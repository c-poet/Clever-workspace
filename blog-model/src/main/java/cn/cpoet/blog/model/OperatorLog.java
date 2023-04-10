package cn.cpoet.blog.model;

import cn.cpoet.blog.model.base.BaseRcEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author CPoet
 */
@Data
@Schema(title = "操作日志")
public class OperatorLog extends BaseRcEntity {
}
