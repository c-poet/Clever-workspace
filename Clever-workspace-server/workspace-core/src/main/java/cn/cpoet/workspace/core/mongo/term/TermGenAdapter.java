package cn.cpoet.workspace.core.mongo.term;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author CPoet
 */
public interface TermGenAdapter {

    /**
     * 支持的注解类型
     *
     * @return 支持注解类型
     */
    Class<? extends Annotation>[] accepts();

    /**
     * 生成语句
     *
     * @param beanClass  类
     * @param field      属性
     * @param annotation 注解
     * @return 生成语句
     */
    String genStatement(Class<?> beanClass, Field field, Annotation annotation);
}
