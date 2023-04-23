package cn.cpoet.blog.core.mongo;

import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.core.vo.RetVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author CPoet
 */
@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomResponseBodyAdvice implements org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @SuppressWarnings({"rawtypes"})
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        RetVO retVO;
        if (body instanceof RetVO) {
            retVO = (RetVO) body;
        } else {
            retVO = RetVO.ok(body);
        }
        if (body instanceof String) {
            try {
                String content = objectMapper.writeValueAsString(retVO);
                // 设置响应的格式为JSON
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                return content;
            } catch (Exception e) {
                throw new BusException("String类型响应体写入失败", e);
            }
        }
        return retVO;
    }
}
