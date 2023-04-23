package cn.cpoet.workspace.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author CPoet
 */
@Data
@Schema(title = "id集合")
public class IdsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "id列表")
    @NotEmpty(message = "id不能为空")
    private List<Long> ids;
}
