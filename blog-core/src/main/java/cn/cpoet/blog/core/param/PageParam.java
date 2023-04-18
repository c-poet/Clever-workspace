package cn.cpoet.blog.core.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author CPoet
 */
@Data
@Schema(title = "分页查询参数")
public class PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(title = "当前页号")
    private Integer pageNo;

    @Schema(title = "每页大小")
    private Integer pageSize;
}
