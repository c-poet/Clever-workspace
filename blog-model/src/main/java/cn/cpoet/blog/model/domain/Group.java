package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CPoet
 */
@Data
@Schema(title = "用户组")
@Document
public class Group extends BaseEntity {
    @Schema(title = "用户组编码")
    private String code;

    @Schema(title = "用户组名")
    private String name;

    @Schema(title = "描述信息")
    private String description;
}
