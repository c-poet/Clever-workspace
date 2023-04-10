package cn.cpoet.blog.api.context;

import cn.cpoet.blog.api.exception.AppException;
import org.springframework.context.ApplicationContext;

/**
 * app上下文holder
 *
 * @author CPoet
 */
public abstract class AppContextHolder {

    private AppContextHolder() {
    }

    private static AppContext appContext;

    /**
     * 设置上下文信息
     *
     * @param appContext 设置上下文
     */
    public static void setContext(AppContext appContext) {
        AppContextHolder.appContext = appContext;
    }

    /**
     * 获取应用上下文
     *
     * @return 应用上下文
     */
    public static AppContext appContext() {
        if (appContext == null) {
            throw new AppException("应用上下文未初始化完成");
        }
        return appContext;
    }

    /**
     * 获取认证上下文
     *
     * @return 认证上下文
     */
    public static AuthContext authContext() {
        return appContext.authContext();
    }

    /**
     * 获取spring上下文
     *
     * @return spring上下文
     */
    public static ApplicationContext applicationContext() {
        return appContext().getBean(ApplicationContext.class);
    }
}
