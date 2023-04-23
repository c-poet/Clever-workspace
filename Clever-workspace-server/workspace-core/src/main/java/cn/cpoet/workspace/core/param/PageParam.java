package cn.cpoet.workspace.core.param;

import cn.cpoet.workspace.api.core.Pageable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author CPoet
 */
@Setter
@EqualsAndHashCode
@Schema(title = "分页查询参数")
public class PageParam implements Pageable, Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(title = "当前页号")
    @NotNull(message = "页号不能为空")
    private Integer pageNo;

    @Schema(title = "每页大小")
    @NotNull(message = "每页大小不能为空")
    private Integer pageSize;


    @Override
    public int getPageNo() {
        return pageNo != null && pageNo > 0 ? pageNo : 0;
    }

    @Override
    public int getPageSize() {
        return pageSize != null && pageSize > 0 ? pageSize : 0;
    }
}
