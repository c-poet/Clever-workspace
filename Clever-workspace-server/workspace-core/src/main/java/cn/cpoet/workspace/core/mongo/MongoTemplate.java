package cn.cpoet.workspace.core.mongo;

import cn.cpoet.workspace.core.mongo.term.QueryGeneratorFactory;
import cn.cpoet.workspace.core.param.PageParam;
import cn.cpoet.workspace.core.vo.PageVO;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 扩展{@link ReactiveMongoTemplate}
 *
 * @author CPoet
 */
public class MongoTemplate extends org.springframework.data.mongodb.core.MongoTemplate {

    private final QueryGeneratorFactory queryGeneratorFactory;

    public MongoTemplate(MongoDatabaseFactory mongoDatabaseFactory,
                         MongoConverter mongoConverter,
                         QueryGeneratorFactory queryGeneratorFactory) {
        super(mongoDatabaseFactory, mongoConverter);
        this.queryGeneratorFactory = queryGeneratorFactory;
    }

    /**
     * 传入查询参数进行查询
     *
     * @param param       查询参数
     * @param entityClass 查询实体
     * @param <T>         实体类型
     * @return 查询结果
     */
    public <T> List<T> findParam(Object param, Class<T> entityClass) {
        return findParam(param, entityClass, null);
    }

    /**
     * 传入查询参数进行查询
     *
     * @param param       查询参数
     * @param entityClass 查询实体
     * @param sort        排序
     * @param <T>         实体类型
     * @return 查询结果
     */
    public <T> List<T> findParam(Object param, Class<T> entityClass, Sort sort) {
        Query query = queryGeneratorFactory.getQuery(param);
        if (sort != null) {
            query = query.with(sort);
        }
        return find(query, entityClass);
    }

    /**
     * 传入查询参数进行分页查询
     *
     * @param param       查询参数
     * @param entityClass 查询实体
     * @param <T>         实体类型
     * @return 查询结果
     */
    public <T> PageVO<T> findPageParam(PageParam param, Class<T> entityClass) {
        return findPageParam(param, entityClass, null);
    }

    /**
     * 传入查询参数进行分页查询
     *
     * @param param       查询参数
     * @param entityClass 查询实体
     * @param sort        排序
     * @param <T>         实体类型
     * @return 查询结果
     */
    public <T> PageVO<T> findPageParam(PageParam param, Class<T> entityClass, Sort sort) {
        Query query = queryGeneratorFactory.getQuery(param);
        if (sort != null) {
            query = query.with(sort);
        }
        List<T> list = find(query, entityClass);
        if (CollectionUtils.isEmpty(list)) {
            return  PageVO.ok(0L, list, param);
        }
        long total = count(query.skip(0).limit(0), entityClass);
        return PageVO.ok(total, list, param);
    }

    /**
     * 传入查询参数进行查询
     *
     * @param param       查询参数
     * @param entityClass 查询实体
     * @param <T>         实体类型
     * @return 查询结果
     */
    public <T> T findOneParam(Object param, Class<T> entityClass) {
        Query query = queryGeneratorFactory.getQuery(param);
        return findOne(query, entityClass);
    }
}
