package cn.cpoet.blog.model.domain;

import cn.cpoet.blog.model.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author CPoet
 */
@Data
@Schema(title = "用户")
@Document("blog_user")
public class User extends BaseEntity {

    @Schema(title = "姓名")
    private String name;

    @Schema(title = "昵称")
    private String nickName;

    @Schema(title = "用户名")
    private String username;

    @Schema(title = "密码")
    private String password;

    @Schema(title = "盐值")
    private String salt;

    @Schema(title = "头像地址")
    private String avatar;

    @Schema(title = "邮箱")
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
