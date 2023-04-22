package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CPoet
 */
@Data
@Schema(title = "文章分类")
@Document("blog_category")
@FieldNameConstants
public class Category extends BaseEntity {
}
