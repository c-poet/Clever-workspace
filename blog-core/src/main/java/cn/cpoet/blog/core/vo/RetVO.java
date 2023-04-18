package cn.cpoet.blog.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author CPoet
 */
@Data
@Schema(title = "统一响应体")
public class RetVO<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(title = "响应码")
    private String code;

    @Schema(title = "响应信息")
    private String message;

    @Schema(title = "响应数据")
    private T data;

    @Schema(title = "响应时间")
    private Long timestamp;
}
