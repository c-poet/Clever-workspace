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
    public static void setAppContext(AppContext appContext) {
        AppContextHolder.appContext = appContext;
    }

    /**
     * 获取应用上下文
     *
     * @return 应用上下文
     */
    public static AppContext getAppContext() {
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
    public static AuthContext getAuthContext() {
        return getAppContext().getAuthContext();
    }

    /**
     * 获取请求上下文
     *
     * @return 请求上下文
     */
    public static WebContext getWebContext() {
        return getAppContext().getWebContext();
    }

    /**
     * 获取spring上下文
     *
     * @return spring上下文
     */
    public static ApplicationContext applicationContext() {
        return getAppContext().getBean(ApplicationContext.class);
    }

    /**
     * 获取bean
     *
     * @param beanClass bean类型
     * @param <T>       bean类型
     * @return bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        return getAppContext().getBean(beanClass);
    }

    /**
     * 获取bean
     *
     * @param beanName  bean名称
     * @param beanClass bean类型
     * @param <T>       bean类型
     * @return bean
     */
    public static <T> T getBean(String beanName, Class<T> beanClass) {
        return getAppContext().getBean(beanName, beanClass);
    }
}
