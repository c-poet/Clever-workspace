package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.api.validation.Insert;
import cn.cpoet.blog.api.validation.Update;
import cn.cpoet.blog.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

/**
 * @author CPoet
 */
@Data
@Schema(title = "用户")
@Document("blog_user")
@FieldNameConstants
public class User extends BaseEntity {

    @Schema(title = "姓名")
    @NotEmpty(message = "姓名不能为空", groups = {Insert.class, Update.class, Default.class})
    @Length(min = 1, max = 8, message = "姓名长度在${min}-${max}之间", groups = {Insert.class, Update.class})
    private String name;

    @Schema(title = "昵称")
    @NotEmpty(message = "昵称不能为空")
    @Length(min = 1, max = 48, message = "昵称长度在${min}-${max}之间")
    private String nickName;

    @Schema(title = "用户名")
    @Indexed(unique = true)
    @NotEmpty(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z_][0-9a-zA-Z_]+$", message = "用户名由字母、数字及下划线组成，且首字符不能为数字")
    private String username;

    @Schema(title = "密码")
    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9_!@#$%^&*]{6}$", message = "密码由字母、数字及_!@#$%^&*字符组成，且长度不少于6位")
    private String password;

    @JsonIgnore
    @Schema(title = "盐值")
    private String salt;

    @Schema(title = "头像地址")
    private String avatar;

    @Schema(title = "邮箱")
    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @Schema(title = "手机号")
    private String mobile;

    @Schema(title = "用户组id")
    private Long groupId;

    @Schema(title = "是否锁定")
    private Boolean locked;

    @Schema(title = "是否启用")
    private Boolean enabled;
}
