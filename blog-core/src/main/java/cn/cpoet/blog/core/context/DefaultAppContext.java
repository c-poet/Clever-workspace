package cn.cpoet.blog.core.context;

import cn.cpoet.blog.api.context.AppContext;
import cn.cpoet.blog.api.context.AppContextHolder;
import cn.cpoet.blog.api.context.AuthContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author CPoet
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultAppContext implements ApplicationContextAware, AppContext {

    private AuthContext authContext;
    private ApplicationContext applicationContext;

    @Override
    public AuthContext authContext() {
        return authContext == null ? (authContext = getBean(AuthContext.class)) : authContext;
    }

    @Override
    public <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanClass) {
        return applicationContext.getBean(beanName, beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContextHolder.setContext(this);
        this.applicationContext = applicationContext;
    }
}
