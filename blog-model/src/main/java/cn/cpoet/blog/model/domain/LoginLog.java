package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.model.base.BaseRcEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CPoet
 */
@Data
@Schema(title = "登录日志")
@Document("blog_login_log")
@FieldNameConstants
public class LoginLog extends BaseRcEntity {
}
