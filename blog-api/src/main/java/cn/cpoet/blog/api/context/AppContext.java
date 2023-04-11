package cn.cpoet.blog.api.context;

/**
 * @author CPoet
 */
public interface AppContext {
    /**
     * 获取认证上下文
     *
     * @return 认证上下文
     */
    AuthContext getAuthContext();

    /**
     * 获取当前请求上下文
     *
     * @return 请求上下文
     */
    WebContext getWebContext();

    /**
     * 获取bean
     *
     * @param beanClass bean类型
     * @param <T>       bean类型
     * @return bean
     */
    <T> T getBean(Class<T> beanClass);

    /**
     * 获取bean
     *
     * @param beanName  bean名称
     * @param beanClass bean类型
     * @param <T>       bean类型
     * @return bean
     */
    <T> T getBean(String beanName, Class<T> beanClass);
}
