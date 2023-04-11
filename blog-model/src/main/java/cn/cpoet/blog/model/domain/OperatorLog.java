package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.model.base.BaseRcEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CPoet
 */
@Data
@Schema(title = "操作日志")
@Document
public class OperatorLog extends BaseRcEntity {
}
