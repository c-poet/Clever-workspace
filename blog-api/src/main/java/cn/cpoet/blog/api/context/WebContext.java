package cn.cpoet.blog.api.context;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import java.util.List;

/**
 * 请求上下文
 *
 * @author CPoet
 */
public interface WebContext {
    /**
     * 获取request信息
     *
     * @return ServerHttpRequest
     */
    ServerHttpRequest getRequest();

    /**
     * 获取response信息
     *
     * @return ServerHttpResponse
     */
    ServerHttpResponse getResponse();

    /**
     * 获取attr值
     *
     * @param name 名称
     * @param <T>  类型
     * @return attr值
     */
    <T> T getAttr(String name);

    /**
     * 设置attr值
     *
     * @param name  名称
     * @param value 值
     */
    void setAttr(String name, Object value);

    /**
     * 获取请求头值
     *
     * @param name 请求头名称
     * @return 值列表
     */
    List<String> getHeaders(String name);

    /**
     * 获取请求头值
     *
     * @param name 请求头名称
     * @return 请求头值
     */
    String getHeader(String name);
}
