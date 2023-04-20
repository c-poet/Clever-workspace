package cn.cpoet.blog.starter.api.admin.dto;

import cn.cpoet.blog.api.validation.Insert;
import cn.cpoet.blog.api.validation.Update;
import cn.cpoet.blog.model.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author CPoet
 */
@Data
@Schema(title = "用户")
public class UserDTO extends User {
    @NotEmpty(message = "密码不能为空", groups = {Insert.class, Update.class})
    @Pattern(regexp = "^[a-zA-Z0-9_!@#$%^&*]{6}$", message = "密码由字母、数字及_!@#$%^&*字符组成，且长度不少于6位", groups = {Insert.class, Update.class})
    private String userPass;
}
