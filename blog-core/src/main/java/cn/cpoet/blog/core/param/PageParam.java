package cn.cpoet.blog.core.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author CPoet
 */
@Data
@Schema(title = "分页查询参数")
public class PageParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(title = "当前页号")
    @NotNull(message = "页号不能为空")
    private Integer pageNo;

    @Schema(title = "每页大小")
    @NotNull(message = "每页大小不能为空")
    private Integer pageSize;

    /**
     * 转换为{@link PageRequest}
     *
     * @return {@link PageRequest}
     */
    public PageRequest toPageRequest() {
        return PageRequest.of(pageNo, pageSize);
    }
}
