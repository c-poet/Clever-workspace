package cn.cpoet.workspace.core.context;

import cn.cpoet.workspace.api.context.AppContext;
import cn.cpoet.workspace.api.context.AppContextHolder;
import cn.cpoet.workspace.api.context.AuthContext;
import cn.cpoet.workspace.api.context.TenantContext;
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
    private TenantContext tenantContext;
    private ApplicationContext applicationContext;

    @Override
    public TenantContext getTenantContext() {
        return tenantContext == null ? (tenantContext = getBean(TenantContext.class)) : tenantContext;
    }

    @Override
    public AuthContext getAuthContext() {
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
        AppContextHolder.setAppContext(this);
        this.applicationContext = applicationContext;
    }
}
