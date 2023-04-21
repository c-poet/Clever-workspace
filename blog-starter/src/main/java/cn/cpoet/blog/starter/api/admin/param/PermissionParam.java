package cn.cpoet.blog.starter.api.admin.param;

import cn.cpoet.blog.api.annotation.term.Like;
import cn.cpoet.blog.core.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author CPoet
 */
@Data
@Schema(title = "功能权限查询")
public class PermissionParam extends PageParam {
    @Like(left = false)
    @Schema(title = "功能编码")
    private String code;

    @Like(left = false)
    @Schema(title = "功能名称")
    private String name;
}
