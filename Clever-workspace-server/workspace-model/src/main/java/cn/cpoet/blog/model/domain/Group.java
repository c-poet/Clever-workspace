package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.api.annotation.Editable;
import cn.cpoet.blog.api.validation.Insert;
import cn.cpoet.blog.api.validation.Update;
import cn.cpoet.blog.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author CPoet
 */
@Data
@Editable
@Schema(title = "用户组")
@Document("blog_group")
@FieldNameConstants
public class Group extends BaseEntity {
    @Schema(title = "父级用户组")
    @NotNull(message = "父级用户组不能为空", groups = {Insert.class, Update.class})
    private Long parentId;

    @Indexed(unique = true)
    @Schema(title = "用户组编码")
    @NotEmpty(message = "用户组编码不能为空", groups = {Insert.class, Update.class})
    private String code;

    @Schema(title = "用户组名")
    @NotEmpty(message = "用户组名不能为空", groups = {Insert.class, Update.class})
    private String name;

    @Schema(title = "是否内置")
    @Editable(value = false)
    private Boolean buildIn;

    @Schema(title = "是否启用")
    private Boolean enabled;

    @Schema(title = "排序值")
    @NotNull(message = "排序值不能为空", groups = {Insert.class, Update.class})
    private Integer order;

    @Schema(title = "描述信息")
    private String description;
}
