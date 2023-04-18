package cn.cpoet.blog.core.vo;

import cn.cpoet.blog.core.constant.Status;
import cn.cpoet.blog.core.constant.StatusConst;
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

    public static <T> RetVO<T> ok(T data) {
        return of(StatusConst.OK, data);
    }

    public static <T> RetVO<T> of(Status status, T data) {
        return of(status.code(), status.message(), data);
    }

    public static <T> RetVO<T> of(String code, String message, T data) {
        RetVO<T> retVO = new RetVO<>();
        retVO.setCode(code);
        retVO.setMessage(message);
        retVO.setData(data);
        return retVO;
    }
}
