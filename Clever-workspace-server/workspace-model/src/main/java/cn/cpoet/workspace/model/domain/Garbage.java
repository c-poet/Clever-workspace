package cn.cpoet.workspace.model.domain;

import cn.cpoet.workspace.api.core.GenMap;
import cn.cpoet.workspace.model.base.BaseRcEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 垃圾数据
 *
 * @author CPoet
 */
@Data
@Document("blog_garbage")
@FieldNameConstants
public class Garbage extends BaseRcEntity {

    @Schema(title = "集合名称")
    private String name;

    @Schema(title = "文档内容")
    private GenMap document;
}
