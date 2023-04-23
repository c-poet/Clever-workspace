package cn.cpoet.workspace.core.mongo.term;

import org.springframework.data.mongodb.core.query.Query;

/**
 * query生成
 *
 * @author CPoet
 */
public interface QueryGenerator<T> {
    /**
     * 生成查询条件
     *
     * @param bean bean
     * @return 查询条件
     */
    Query generate(T bean);
}
