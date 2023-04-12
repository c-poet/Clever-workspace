package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CPoet
 */
@Data
@Schema(title = "权限/资源")
@Document("blog_permission")
public class Permission extends BaseEntity {

}
