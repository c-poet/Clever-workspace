package cn.cpoet.workspace.starter.api.admin.param;

import cn.cpoet.workspace.api.annotation.term.Eq;
import cn.cpoet.workspace.api.annotation.term.Like;
import cn.cpoet.workspace.api.annotation.term.Order;
import cn.cpoet.workspace.api.constant.OrderMode;
import cn.cpoet.workspace.core.param.PageOrderParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author CPoet
 */
@Data
@Schema(title = "用户查询参数")
public class UserParam extends PageOrderParam {

    private static final long serialVersionUID = 1L;

    @Schema(title = "姓名")
    @Like(left = false)
    private String name;

    @Schema(title = "用户名")
    @Like(left = false)
    private String username;

    @Schema(title = "昵称")
    @Like(left = false)
    private String nickName;

    @Schema(title = "邮箱")
    @Like(left = false)
    private String email;

    @Schema(title = "手机号")
    @Like(left = false)
    private String mobile;

    @Eq
    @Schema(title = "用户组")
    private Long groupId;

    @Eq
    @Schema(title = "是否锁定")
    private Boolean locked;

    @Eq
    @Schema(title = "是否启用")
    private Boolean enabled;

    @Order(value = OrderMode.DESC, fields = {"name", "username"})
    private List<String> order;
}
