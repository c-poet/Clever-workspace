package cn.cpoet.workspace.auth.support;

import cn.cpoet.workspace.api.context.AppContextHolder;
import cn.cpoet.workspace.api.core.AccessorHandler;
import org.springframework.beans.BeanUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 访问处理对象工厂
 *
 * @author CPoet
 */
public abstract class AccessorHandlerFactory {

    private final static Map<Class<?>, AccessorHandler> BEAN_CACHE = new ConcurrentHashMap<>();

    private AccessorHandlerFactory() {
    }

    /**
     * 获取访问对象
     *
     * @param clazz 访问处理对象
     * @return 访问处理对象
     */
    public static AccessorHandler get(Class<? extends AccessorHandler> clazz) {
        AccessorHandler bean = BEAN_CACHE.get(clazz);
        if (bean != null) {
            return bean;
        }
        if (AccessorHandler.class == clazz || DefaultAccessorHandler.class == clazz) {
            return DefaultAccessorHandler.INSTANCE;
        }
        try {
            return AppContextHolder.getBean(clazz);
        } catch (Exception ignored) {
        }
        bean = BeanUtils.instantiateClass(clazz);
        BEAN_CACHE.put(clazz, bean);
        return bean;
    }
}
