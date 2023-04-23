package cn.cpoet.workspace.core.support;

import cn.cpoet.workspace.core.module.Module;
import cn.cpoet.workspace.core.module.ModuleHolder;
import cn.cpoet.workspace.core.module.Rewrite;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.StringUtils;

/**
 * @author CPoet
 */
@Slf4j
public class CustomAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {
    /**
     * 重写时父模块和子模块名称分隔符
     */
    private final static String REWRITE_BEAN_PARENT_SEPARATOR = ".";

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanName = super.generateBeanName(definition, registry);
        String beanClassName = definition.getBeanClassName();
        if (StringUtils.hasText(beanClassName)) {
            int index = beanClassName.lastIndexOf(".");
            if (index != -1) {
                String beanNamePrefix = getBeanNamePrefix(beanClassName.substring(0, index), true);
                if (StringUtils.hasText(beanNamePrefix)) {
                    beanName = beanNamePrefix + beanName;
                }
            }
        }
        return beanName;
    }

    /**
     * 获取bean名称前缀
     *
     * @param packageName 包名
     * @param isRoot      递归深度
     * @return bean名称前缀
     */
    private String getBeanNamePrefix(String packageName, boolean isRoot) {
        Module module = ModuleHolder.getModule(packageName);
        if (module == null) {
            int index = packageName.lastIndexOf(".");
            // 向上查找
            return index == -1 ? null : getBeanNamePrefix(packageName.substring(0, index), isRoot);
        }
        Rewrite rewriteBean = module.getRewrite();
        if (!rewriteBean.isEnabled()) {
            return null;
        }
        String prefix = rewriteBean.getPrefix();
        if (!StringUtils.hasText(prefix)) {
            prefix = module.getName();
        }
        // 是否拼接父模块信息
        if (rewriteBean.isParent()) {
            int index = packageName.lastIndexOf(".");
            if (index != -1) {
                String parentPrefix = getBeanNamePrefix(packageName.substring(0, index), false);
                if (StringUtils.hasText(parentPrefix)) {
                    prefix = parentPrefix + REWRITE_BEAN_PARENT_SEPARATOR + prefix;
                }
            }
        }
        return isRoot ? prefix + rewriteBean.getSeparator() : prefix;
    }
}
