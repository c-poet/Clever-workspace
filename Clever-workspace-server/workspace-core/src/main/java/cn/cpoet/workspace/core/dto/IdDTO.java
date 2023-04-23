package cn.cpoet.workspace.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author CPoet
 */
@Data
@Schema(title = "id信息")
public class IdDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "id")
    @NotNull(message = "id不能为空")
    private Long id;
}
