package cn.cpoet.workspace.module.admin.param;

import cn.cpoet.workspace.api.annotation.term.Eq;
import cn.cpoet.workspace.api.annotation.term.Like;
import cn.cpoet.workspace.core.param.PageOrderParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author CPoet
 */
@Data
@Schema(title = "功能权限查询")
public class PermissionParam extends PageOrderParam {
    @Like(left = false)
    @Schema(title = "功能编码")
    private String code;

    @Like(left = false)
    @Schema(title = "功能名称")
    private String name;

    @Eq
    @Schema(title = "是否启用")
    private Boolean enabled;
}
