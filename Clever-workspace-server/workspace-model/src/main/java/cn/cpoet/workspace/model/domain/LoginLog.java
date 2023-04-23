package cn.cpoet.workspace.model.domain;

import cn.cpoet.workspace.model.base.BaseRcEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author CPoet
 */
@Data
@Schema(title = "登录日志")
@Document("blog_login_log")
@FieldNameConstants
public class LoginLog extends BaseRcEntity {
    @Schema(title = "登录用户uid")
    private Long userId;

    @Schema(title = "登录用户的姓名")
    private String name;

    @Schema(title = "登录用户的账号")
    private String username;

    @Schema(title = "登录用户的昵称")
    private String nickName;

    @Schema(title = "登录用户的邮箱")
    private String email;

    @Schema(title = "登录的ip地址")
    private String ipAddress;

    @Schema(title = "登录UA")
    private String userAgent;

    @Schema(title = "其它描述性说明")
    private String detail;

    @Schema(title = "登录时间")
    private LocalDateTime loginTime;
}
