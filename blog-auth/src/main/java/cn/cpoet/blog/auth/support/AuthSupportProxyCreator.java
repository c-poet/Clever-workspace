package cn.cpoet.blog.auth.support;

import cn.cpoet.blog.api.annotation.Accessor;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author CPoet
 */
@Component
@RequiredArgsConstructor
public class AuthSupportProxyCreator extends AbstractAutoProxyCreator {

    private static final long serialVersionUID = -3649232791645307626L;

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {
        final Map<String, AuthTargetInfo> authTargetInfoMapper = genAuthTargetInfo(beanClass);
        if (CollectionUtils.isEmpty(authTargetInfoMapper)) {
            return DO_NOT_PROXY;
        }
        return new Object[]{new AuthSupportAdvisor(authTargetInfoMapper)};
    }

    /**
     * 获取认证代理信息
     *
     * @param beanClass 目标类
     * @return 认证代理信息
     */
    private Map<String, AuthTargetInfo> genAuthTargetInfo(Class<?> beanClass) {
        Accessor accessor = beanClass.getAnnotation(Accessor.class);
        return null;
    }
}
