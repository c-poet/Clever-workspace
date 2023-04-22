package cn.cpoet.blog.core.util;

import cn.cpoet.blog.api.core.ExchangeTool;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;

/**
 * 请求工具
 *
 * @author CPoet
 */
public abstract class RequestUtil {

    private final static String HEAD_USER_AGENT = "UserAgent";

    private RequestUtil() {
    }

    /**
     * 获取ip地址
     *
     * @param request 请求
     * @return ip地址
     */
    public static String getIpAddr(ServerHttpRequest request) {
        InetSocketAddress address = request.getRemoteAddress();
        return address == null ? null : address.getHostString();
    }

    /**
     * 获取代理信息
     *
     * @param request 请求
     * @return 代理信息
     */
    public static String getUserAgent(ServerHttpRequest request) {
        return ExchangeTool.getHeader(request, HEAD_USER_AGENT);
    }
}