package cn.cpoet.blog.auth.support;

import cn.cpoet.blog.api.core.AccessorMeta;
import lombok.RequiredArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author CPoet
 */
@RequiredArgsConstructor
public class AuthSupportAdvisor implements Advisor, MethodBeforeAdvice {

    private final Map<String, AccessorMeta> AMMapper;

    @Override
    public Advice getAdvice() {
        return this;
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }

    @Override
    public void before(Method method, Object[] args, Object target) {
    }
}
