package cn.cpoet.blog.starter.api.admin.vo;

import cn.cpoet.blog.model.domain.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author CPoet
 */
@Data
@Schema(title = "权限节点")
public class PermissionNodeVO extends Permission {
    @Schema(title = "子级权限")
    private List<PermissionNodeVO> children;

    public static PermissionNodeVO of(Permission permission) {
        PermissionNodeVO permissionNodeVO = new PermissionNodeVO();
        BeanUtils.copyProperties(permission, permissionNodeVO);
        return permissionNodeVO;
    }
}
