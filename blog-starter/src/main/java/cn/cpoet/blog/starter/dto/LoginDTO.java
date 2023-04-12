package cn.cpoet.blog.starter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author CPoet
 */
@Data
@Schema(title = "登录信息")
public class LoginDTO {
    @Schema(title = "用户名")
    @NotEmpty(message = "请输入用户名")
    private String username;

    @Schema(title = "密码")
    @NotEmpty(message = "请输入密码")
    private String password;
}
