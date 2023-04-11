package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CPoet
 */
@Data
@Schema(title = "用户")
@Document
public class User extends BaseEntity {
    @Schema(title = "用户名")
    private String username;

    @Schema(title = "用户组id")
    private Long groupId;
}
