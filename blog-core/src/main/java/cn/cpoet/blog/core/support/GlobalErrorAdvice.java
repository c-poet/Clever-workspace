package cn.cpoet.blog.core.support;

import cn.cpoet.blog.core.constant.Status;
import cn.cpoet.blog.core.constant.StatusConst;
import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.core.vo.RetVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
@Slf4j
@RestControllerAdvice
public class GlobalErrorAdvice {
    @ExceptionHandler(BusException.class)
    public Mono<RetVO<?>> busException(BusException e) {
        log.info("业务异常：{}", e.getMessage(), e);
        return Mono.fromSupplier(() -> {
            RetVO<?> retVO = new RetVO<>();
            Status status = e.getStatus();
            retVO.setCode(status.code());
            retVO.setMessage(status.message());
            return retVO;
        });
    }

    @ExceptionHandler(Exception.class)
    public Mono<RetVO<?>> exception(Exception e) {
        log.warn("系统异常：{}", e.getMessage(), e);
        return Mono.fromSupplier(() -> {
            RetVO<?> retVO = new RetVO<>();
            retVO.setCode(StatusConst.ERROR.code());
            retVO.setMessage(StatusConst.ERROR.message());
            return retVO;
        });
    }
}
