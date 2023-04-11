package cn.cpoet.blog.auth.support;

import cn.cpoet.blog.api.annotation.Accessor;
import cn.cpoet.blog.api.core.AccessorMeta;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator;
import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author CPoet
 */
@Component
@RequiredArgsConstructor
public class AuthSupportProxyCreator extends AbstractAutoProxyCreator {

    private static final long serialVersionUID = -3649232791645307626L;

    @Override
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName, TargetSource customTargetSource) throws BeansException {
        final Map<String, AccessorMeta> accessorMetaMapper = genAuthTargetInfo(beanClass);
        if (CollectionUtils.isEmpty(accessorMetaMapper)) {
            return DO_NOT_PROXY;
        }
        return new Object[]{new AuthSupportAdvisor(accessorMetaMapper)};
    }

    /**
     * 获取认证代理信息
     *
     * @param beanClass 目标类
     * @return 认证代理信息
     */
    private Map<String, AccessorMeta> genAuthTargetInfo(Class<?> beanClass) {
        // 获取Public所有方法
        Method[] methods = beanClass.getMethods();
        if (methods.length == 0) {
            return Collections.emptyMap();
        }
        List<AccessorMeta> accessorMetas = new ArrayList<>(methods.length);
        Set<Accessor> beanAccessors = AnnotatedElementUtils.findAllMergedAnnotations(beanClass, Accessor.class);
        for (Method method : methods) {
            Set<Accessor> methodAccessors = AnnotatedElementUtils.findAllMergedAnnotations(method, Accessor.class);
            if (!CollectionUtils.isEmpty(methodAccessors) || !CollectionUtils.isEmpty(beanAccessors)) {

            }
        }
        if (CollectionUtils.isEmpty(accessorMetas)) {
            return Collections.emptyMap();
        }
        return accessorMetas.stream()
            .collect(Collectors.toMap(AccessorMeta::getId, Function.identity()));
    }
}
