package cn.cpoet.workspace.auth.resolver;

import cn.cpoet.workspace.api.context.AppContextHolder;
import cn.cpoet.workspace.api.context.Subject;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 认证主体方法参数解析器
 *
 * @author wanggf
 */
public class SubjectArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return Subject.class == methodParameter.getParameterType();
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return AppContextHolder.getAuthContext().curSubject();
    }
}
