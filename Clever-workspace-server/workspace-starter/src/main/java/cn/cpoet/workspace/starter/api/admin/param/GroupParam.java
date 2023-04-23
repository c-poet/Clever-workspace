package cn.cpoet.workspace.starter.api.admin.param;

import cn.cpoet.workspace.api.annotation.term.Eq;
import cn.cpoet.workspace.core.param.PageOrderParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author CPoet
 */
@Data
@Schema(title = "分组参数")
public class GroupParam extends PageOrderParam {
    @Eq
    @Schema(title = "是否启用")
    private Boolean enabled;
}
