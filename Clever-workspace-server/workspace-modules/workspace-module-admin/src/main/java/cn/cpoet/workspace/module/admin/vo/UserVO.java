package cn.cpoet.workspace.module.admin.vo;

import cn.cpoet.workspace.model.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author CPoet
 */
@Data
@Schema(title = "用户信息")
public class UserVO extends User {
    @Schema(title = "用户组编码")
    private String groupCode;

    @Schema(title = "用户组名")
    private String groupName;

    @Schema(title = "最后一次登录的Ip")
    private String lastLoginIp;

    @Schema(title = "最后一次登录的时间")
    private LocalDateTime lastLoginTime;

    public static UserVO of(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
