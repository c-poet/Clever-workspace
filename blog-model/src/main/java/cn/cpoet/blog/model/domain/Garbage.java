package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.api.core.GenMap;
import cn.cpoet.blog.model.base.BaseRcEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 垃圾数据
 *
 * @author CPoet
 */
@Data
@Document("blog_garbage")
public class Garbage extends BaseRcEntity {

    @Indexed
    @Schema(title = "文档id")
    private Long documentId;

    @Schema(title = "集合名称")
    private String collectionName;

    @Schema(title = "文档内容")
    private GenMap entity;

    @Schema(title = "实体类型")
    private String entityClass;
}
