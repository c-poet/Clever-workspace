package cn.cpoet.blog.auth.support;

import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.api.core.AccessorHandler;
import cn.cpoet.blog.api.core.AccessorMeta;

import java.lang.reflect.Method;

/**
 * 默认访问处理器
 *
 * @author CPoet
 */
public class DefaultAccessorHandler implements AccessorHandler {

    public final static AccessorHandler INSTANCE = new DefaultAccessorHandler();

    @Override
    public boolean accept(Subject subject, Object obj, Method method, AccessorMeta meta) {
        return false;
    }
}
