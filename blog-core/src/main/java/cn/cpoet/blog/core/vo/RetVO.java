package cn.cpoet.blog.core.vo;

import cn.cpoet.blog.core.constant.StatusConst;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 获取响应消息
     *
     * @param mono 响应内容
     * @param <T>  响应类型
     * @return 响应内容
     */
    public static <T> Mono<RetVO<T>> of(Mono<T> mono) {
        return mono.map(data -> {
            RetVO<T> retVO = new RetVO<>();
            retVO.setCode(StatusConst.OK.code());
            retVO.setMessage(StatusConst.OK.message());
            retVO.setData(data);
            return retVO;
        });
    }

    /**
     * 获取响应消息
     *
     * @param flux 响应内容
     * @param <T>  响应类型
     * @return 响应内容
     */
    public static <T> Mono<RetVO<List<T>>> of(Flux<T> flux) {
        return flux.reduceWith(() -> {
            RetVO<List<T>> retVO = new RetVO<>();
            retVO.setCode(StatusConst.OK.code());
            retVO.setMessage(StatusConst.OK.message());
            retVO.setData(new ArrayList<>());
            return retVO;
        }, (ret, item) -> {
            ret.getData().add(item);
            return ret;
        });
    }
}
