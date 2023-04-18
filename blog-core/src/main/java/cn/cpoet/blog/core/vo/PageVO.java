package cn.cpoet.blog.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author CPoet
 */
@Data
@Schema(title = "分页结果响应")
public class PageVO<T> extends RetVO<List<T>> {
    @Schema(title = "数据总计")
    private Long total;

    @Schema(title = "当前页号")
    private Integer pageNo;

    @Schema(title = "分页大小")
    private Integer pageSize;
}
