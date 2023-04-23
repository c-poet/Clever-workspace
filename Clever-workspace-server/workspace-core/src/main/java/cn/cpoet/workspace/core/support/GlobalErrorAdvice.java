package cn.cpoet.workspace.core.support;

import cn.cpoet.workspace.core.constant.Status;
import cn.cpoet.workspace.core.constant.StatusConst;
import cn.cpoet.workspace.core.exception.BusException;
import cn.cpoet.workspace.core.vo.RetVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

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
    public RetVO<?> busException(BusException e) {
        log.info("业务异常：{}", e.getMessage(), e);
        RetVO<?> retVO = new RetVO<>();
        Status status = e.getStatus();
        retVO.setCode(status.code());
        retVO.setMessage(status.message());
        return retVO;
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public RetVO<?> webExchangeBindException(WebExchangeBindException e) {
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
        return retVO;
    }

    @ExceptionHandler(Exception.class)
    public RetVO<?> exception(Exception e) {
        log.warn("系统异常：{}", e.getMessage(), e);
        RetVO<?> retVO = new RetVO<>();
        retVO.setCode(StatusConst.ERROR.code());
        retVO.setMessage(StatusConst.ERROR.message());
        return retVO;
    }
}
