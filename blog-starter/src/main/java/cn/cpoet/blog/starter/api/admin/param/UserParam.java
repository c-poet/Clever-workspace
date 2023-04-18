package cn.cpoet.blog.starter.api.admin.param;

import cn.cpoet.blog.core.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author CPoet
 */
@Data
@Schema(title = "用户查询参数")
public class UserParam extends PageParam {
    private static final long serialVersionUID = 1L;
}
