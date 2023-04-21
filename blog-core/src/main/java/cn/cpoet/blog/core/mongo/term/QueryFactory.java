package cn.cpoet.blog.core.mongo.term;


import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * 查询工厂
 *
 * @author CPoet
 */
@Component
@RequiredArgsConstructor
public class QueryFactory {

    private final QueryGeneratorFactory generatorFactory;

    /**
     * 获取查询信息
     *
     * @param bean bean
     * @return 查询信息
     */
    @SuppressWarnings("unchecked")
    public Query get(Object bean) {
        QueryGenerator<Object> queryGenerator = generatorFactory.get((Class<Object>) bean.getClass());
        return queryGenerator.generate(bean);
    }
}
