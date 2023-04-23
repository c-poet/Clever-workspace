package cn.cpoet.blog.core.support;

import cn.cpoet.blog.core.module.Mapping;
import cn.cpoet.blog.core.module.Module;
import cn.cpoet.blog.core.module.ModuleHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @author CPoet
 */
@Component
public class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final static String PATH_SEPARATOR = "/";

    @Override
    public void setPathPrefixes(Map<String, Predicate<Class<?>>> prefixes) {
        super.setPathPrefixes(prefixes);
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        if (info != null) {
            String customPath = getCustomPath(handlerType.getName());
            if (StringUtils.hasText(customPath)) {
                info = RequestMappingInfo.paths(customPath).build().combine(info);
            }
        }
        return info;
    }

    /**
     * 获取模块路径定义
     *
     * @param name 所在限定名
     * @return 路径定义信息
     */
    private String getCustomPath(String name) {
        String path = null;
        Module module = ModuleHolder.getModuleRbyClass(name);
        if (module != null) {
            Mapping mapping = module.getMapping();
            if (mapping != null) {
                path = mapping.getPath();
                String parentPath = getCustomPath(module.getPath());
                if (StringUtils.hasText(parentPath)) {
                    if (!StringUtils.hasText(path)) {
                        path = parentPath;
                    } else if (parentPath.endsWith(PATH_SEPARATOR)) {
                        return parentPath + (path.startsWith(PATH_SEPARATOR) ? path.substring(1) : path);
                    } else {
                        return parentPath + (path.startsWith(PATH_SEPARATOR) ? path : (PATH_SEPARATOR + path));
                    }
                }
            }
        }
        return path;
    }
}
