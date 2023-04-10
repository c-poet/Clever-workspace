package cn.cpoet.blog.model;

import cn.cpoet.blog.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author CPoet
 */
@Data
@Schema(title = "用户")
public class User extends BaseEntity {


    @Schema(title = "用户组id")
    private Long groupId;
}
