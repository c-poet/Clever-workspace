package cn.cpoet.blog.core.support;

import cn.cpoet.blog.core.constant.Status;
import cn.cpoet.blog.core.constant.StatusConst;
import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.core.vo.RetVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<RetVO<?>> webExchangeBindException(WebExchangeBindException e) {
        RetVO<Object> retVO = new RetVO<>();
        retVO.setCode(StatusConst.FAILED.code());
        FieldError firstError = e.getFieldError();
        if (firstError != null) {
            retVO.setMessage(firstError.getDefaultMessage());
            List<FieldError> fieldErrors = e.getFieldErrors();
            Map<String, String> fieldErrorMapping = new HashMap<>(fieldErrors.size());
            for (FieldError fieldError : fieldErrors) {
                fieldErrorMapping.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            retVO.setData(fieldErrorMapping);
        }
        return Mono.just(retVO);
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
