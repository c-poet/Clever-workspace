package cn.cpoet.blog.api.context;

/**
 * 请求上下文Holder
 *
 * @author CPoet
 */
public abstract class RequestContextHolder {

    private final static ThreadLocal<WebContext> REQS_CONTEXT_TL = new ThreadLocal<>();

    private RequestContextHolder() {
    }

    /**
     * 获取当前请求上下文中的信息
     *
     * @return 请求信息
     */
    public static WebContext get() {
        return REQS_CONTEXT_TL.get();
    }

    /**
     * 设置请求信息
     *
     * @param requestContext 请求信息
     */
    public static void set(WebContext requestContext) {
        REQS_CONTEXT_TL.set(requestContext);
    }

    /**
     * 清除请求信息
     */
    public static void clear() {
        REQS_CONTEXT_TL.remove();
    }
}
