package cn.cpoet.blog.model;

import cn.cpoet.blog.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author CPoet
 */
@Data
@Schema(title = "用户组")
public class Group extends BaseEntity {
    @Schema(title = "用户组编码")
    private String code;

    @Schema(title = "用户组名")
    private String name;

    @Schema(title = "描述信息")
    private String description;
}
