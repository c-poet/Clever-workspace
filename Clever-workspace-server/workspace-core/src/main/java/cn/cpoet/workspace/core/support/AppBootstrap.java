package cn.cpoet.workspace.core.support;

import cn.cpoet.workspace.api.exception.AppException;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ResourceLoader;

import java.util.Collection;

/**
 * @author CPoet
 */
public class AppBootstrap extends SpringApplication {

    public AppBootstrap(Class<?>... primarySources) {
        super(primarySources);
    }

    public AppBootstrap(ResourceLoader resourceLoader, Class<?>... primarySources) {
        super(resourceLoader, primarySources);
    }

    @Override
    public void setInitializers(Collection<? extends ApplicationContextInitializer<?>> initializers) {
        super.setInitializers(initializers);
    }

    @Override
    protected ConfigurableApplicationContext createApplicationContext() {
        ConfigurableApplicationContext applicationContext = super.createApplicationContext();
        try {
            // 设置bean名称生成器
            if (applicationContext instanceof AnnotationConfigServletWebServerApplicationContext) {
                AnnotationBeanNameGenerator annotationBeanNameGenerator = new CustomAnnotationBeanNameGenerator();
                ((AnnotationConfigServletWebServerApplicationContext) applicationContext).setBeanNameGenerator(annotationBeanNameGenerator);
            }
            if (applicationContext instanceof AnnotationConfigApplicationContext) {
                AnnotationBeanNameGenerator annotationBeanNameGenerator = new CustomAnnotationBeanNameGenerator();
                ((AnnotationConfigApplicationContext) applicationContext).setBeanNameGenerator(annotationBeanNameGenerator);
            }
        } catch (Exception e) {
            throw new AppException("定义应用上下文失败", e);
        }
        return applicationContext;
    }

    public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
        return run(new Class[]{primarySource}, args);
    }

    public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
        AppBootstrap appBootstrap = new AppBootstrap(primarySources);
        // 关闭banner
        appBootstrap.setBannerMode(Banner.Mode.OFF);
        return appBootstrap.run(args);
    }
}
