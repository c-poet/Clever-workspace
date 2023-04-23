package cn.cpoet.workspace.core.mongo.term;

import org.springframework.data.mongodb.core.query.Query;

/**
 * 查询生成器工厂
 *
 * @author CPoet
 */
public interface QueryGeneratorFactory {
    /**
     * 获取查询对象
     *
     * @param bean 实体对象
     * @return 查询对象
     */
    Query getQuery(Object bean);

    /**
     * 获取查询生成器
     *
     * @param bean 实体对象
     * @param <T>  实体类型
     * @return 查询生成器
     */
    <T> QueryGenerator<T> get(T bean);

    /**
     * 获取查询生成器
     *
     * @param beanClass 实体类型
     * @param <T>       实体类型
     * @return 查询生成器
     */
    <T> QueryGenerator<T> get(Class<T> beanClass);
}
