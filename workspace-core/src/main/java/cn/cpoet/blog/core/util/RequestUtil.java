package cn.cpoet.blog.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求工具
 *
 * @author CPoet
 */
public abstract class RequestUtil {

    private final static String USER_AGENT_HEADER = "UserAgent";

    private RequestUtil() {
    }

    /**
     * 获取UserAgent串
     *
     * @param request 请求
     * @return  UserAgent
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader(USER_AGENT_HEADER);
    }

    /**
     * 根据request，获取ip地址
     *
     * @param request {@link  HttpServletRequest}
     * @return ip地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
