package cn.cpoet.workspace.model.domain;

import cn.cpoet.workspace.api.annotation.Editable;
import cn.cpoet.workspace.api.validation.Insert;
import cn.cpoet.workspace.api.validation.Update;
import cn.cpoet.workspace.model.base.BaseEntity;
import cn.cpoet.workspace.model.constant.SexEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author CPoet
 */
@Data
@Editable
@Schema(title = "用户")
@Document("blog_user")
@FieldNameConstants
public class User extends BaseEntity {

    @Schema(title = "姓名")
    @NotEmpty(message = "姓名不能为空", groups = {Insert.class, Update.class,})
    @Length(min = 1, max = 8, message = "姓名长度在${min}-${max}之间", groups = {Insert.class, Update.class})
    private String name;

    @Schema(title = "昵称")
    @NotEmpty(message = "昵称不能为空", groups = {Insert.class, Update.class})
    @Length(min = 1, max = 48, message = "昵称长度在${min}-${max}之间", groups = {Insert.class, Update.class})
    private String nickName;

    @Schema(title = "用户名")
    @Indexed(unique = true)
    @NotEmpty(message = "用户名不能为空", groups = {Insert.class, Update.class})
    @Pattern(regexp = "^[a-zA-Z_][0-9a-zA-Z_]+$", message = "用户名由字母、数字及下划线组成，且首字符不能为数字", groups = {Insert.class, Update.class})
    private String username;

    @JsonIgnore
    @Editable(value = false)
    @Schema(title = "密码")
    private String password;

    @JsonIgnore
    @Editable(value = false)
    @Schema(title = "盐值")
    private String salt;

    @Schema(title = "头像地址")
    private String avatar;

    @Schema(title = "邮箱")
    @NotEmpty(message = "邮箱不能为空", groups = {Insert.class, Update.class})
    @Email(message = "邮箱格式不正确", groups = {Insert.class, Update.class})
    private String email;

    @Schema(title = "手机号")
    private String mobile;

    @Schema(title = "性别")
    @NotNull(message = "性别不能为空", groups = {Insert.class, Update.class})
    private SexEnum sex;

    @Schema(title = "用户组id")
    @NotNull(message = "用户组不能为空", groups = {Insert.class, Update.class})
    private Long groupId;

    @Editable(value = false)
    @Schema(title = "是否内置")
    private Boolean buildIn;

    @Schema(title = "是否锁定")
    private Boolean locked;

    @Schema(title = "是否启用")
    private Boolean enabled;
}
