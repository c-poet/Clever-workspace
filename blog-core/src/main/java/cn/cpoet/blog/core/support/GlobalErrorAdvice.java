package cn.cpoet.blog.core.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CPoet
 */
@Slf4j
@RestControllerAdvice
public class GlobalErrorAdvice {
    @ExceptionHandler(Exception.class)
    public Mono<Map<String, Object>> exception(Exception e) {
        log.warn("系统异常：{}", e.getMessage(), e);
        return Mono.fromSupplier(() -> {
            Map<String, Object> map = new HashMap<>(1 << 4);
            map.put("msg", e.getMessage());
            return map;
        });
    }
}
