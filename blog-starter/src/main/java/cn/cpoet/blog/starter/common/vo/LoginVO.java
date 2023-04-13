package cn.cpoet.blog.starter.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author CPoet
 */
@Data
@Schema(title = "身份凭证")
public class LoginVO {
    @Schema(title = "用户id")
    private Long userId;

    @Schema(title = "凭证值")
    private String token;
}
