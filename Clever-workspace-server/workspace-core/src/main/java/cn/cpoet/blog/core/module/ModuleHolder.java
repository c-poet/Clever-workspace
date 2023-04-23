package cn.cpoet.blog.core.module;

import cn.cpoet.blog.api.exception.AppException;
import javassist.ClassPool;
import javassist.CtClass;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模块信息类动态操作类
 *
 * @author CPoet
 */
public abstract class ModuleHolder {

    private final static Map<String, Module> MODULE_CACHE = new ConcurrentHashMap<>();

    public final static String PACKAGE_INFO = "package-info";
    public final static String PACKAGE_INFO_CLASS = PACKAGE_INFO + ".class";

    private ModuleHolder() {
    }

    /**
     * 查询当前类所在包信息
     *
     * @param className 全限定类名
     * @return 模块信息
     */
    public static Module getModuleByClass(String className) {
        int index = className.lastIndexOf(".");
        return getModule(className.substring(0, index));
    }

    /**
     * 查找模块信息，当前类所在包不存在则向上查询
     *
     * @param className 全限定类名
     * @return 模块信息
     */
    public static Module getModuleRbyClass(String className) {
        int index = className.lastIndexOf(".");
        return getModuleR(className.substring(0, index));
    }

    /**
     * 获取模块信息
     *
     * @param packageName 包名
     * @return 模块信息
     */
    public static Module getModule(String packageName) {
        if (MODULE_CACHE.containsKey(packageName)) {
            return MODULE_CACHE.get(packageName);
        }
        cn.cpoet.blog.api.annotation.Module moduleAnnotation = getModuleAnnotation(packageName);
        if (moduleAnnotation == null) {
            moduleAnnotation = getModuleAnnotationByCt(packageName);
        }
        Module module = createModule(packageName, moduleAnnotation);
        // 不为null时才缓存，避免垃圾缓存
        if (module != null) {
            // 获取父级模块信息
            int index = module.getPath().lastIndexOf(".");
            if (index != -1) {
                Module parentModule = getModuleR(module.getPath().substring(0, index));
                module.setParent(parentModule);
            }
            MODULE_CACHE.put(packageName, module);
        }
        return module;
    }

    /**
     * 获取模块信息并向上递归查找
     *
     * @param packageName 包名
     * @return 模块信息
     */
    public static Module getModuleR(String packageName) {
        Module module = getModule(packageName);
        if (module != null) {
            return module;
        }
        int index = packageName.lastIndexOf(".");
        return index == -1 ? null : getModuleR(packageName.substring(0, index));
    }

    private static Module createModule(String packageName, cn.cpoet.blog.api.annotation.Module moduleAnnotation) {
        if (moduleAnnotation == null) {
            return null;
        }
        Module module = new Module();
        module.setPath(packageName);
        module.setName(moduleAnnotation.name());
        module.setDesc(moduleAnnotation.description());
        cn.cpoet.blog.api.annotation.Rewrite rewriteBeanAnnotation = moduleAnnotation.rewrite();
        Rewrite rewrite = new Rewrite();
        rewrite.setEnabled(rewriteBeanAnnotation.value());
        rewrite.setParent(rewriteBeanAnnotation.parent());
        rewrite.setPrefix(rewriteBeanAnnotation.prefix());
        rewrite.setSeparator(rewriteBeanAnnotation.separator());
        module.setRewrite(rewrite);
        cn.cpoet.blog.api.annotation.Mapping mappingAnnotation = moduleAnnotation.mapping();
        if (StringUtils.hasText(mappingAnnotation.path())) {
            Mapping mapping = new Mapping();
            mapping.setPath(mappingAnnotation.path());
            module.setMapping(mapping);
        }
        return module;
    }

    /**
     * 通过{@link Package}类直接获取包信息，存在失败的情况
     *
     * @param packageName 名称
     * @return 模块注解信息
     */
    private static cn.cpoet.blog.api.annotation.Module getModuleAnnotation(String packageName) {
        Package pck = Package.getPackage(packageName);
        if (pck == null) {
            return null;
        }
        return pck.getAnnotation(cn.cpoet.blog.api.annotation.Module.class);
    }

    /**
     * 通过读取package-info文件的方式获取注解信息
     * <p>1, 在jvm未加载包下面类时，通过{@link  Package}获取的包为null值</p>
     * <p>2, 通过javassist直接解析package-info文件获取</p>
     *
     * @param packageName 包名
     * @return 模块注解信息
     */
    private static cn.cpoet.blog.api.annotation.Module getModuleAnnotationByCt(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 获取package-info文件路径
        String packageInfoFile = packageName.replaceAll("\\.", "/") + "/" + PACKAGE_INFO_CLASS;
        try (InputStream in = classLoader.getResourceAsStream(packageInfoFile)) {
            if (in == null) {
                return null;
            }
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = classPool.makeClass(in);
            return (cn.cpoet.blog.api.annotation.Module) ctClass.getAnnotation(cn.cpoet.blog.api.annotation.Module.class);
        } catch (Exception e) {
            throw new AppException("读取模块元信息失败", e);
        }
    }
}
