package cn.cpoet.blog.auth.support;

import cn.cpoet.blog.api.core.AccessorMeta;
import cn.cpoet.blog.auth.util.MethodUtil;
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
        if (target != null) {
            String methodId = MethodUtil.getMethodId(target.getClass(), method);
            AccessorMeta accessorMeta = AMMapper.get(methodId);
            if (accessorMeta != null) {
                // 处理认证逻辑
            }
        }
    }
}
