package cn.cpoet.blog.core.mongo;

import cn.cpoet.blog.core.mongo.term.QueryGeneratorFactory;
import cn.cpoet.blog.core.param.PageParam;
import cn.cpoet.blog.core.vo.PageVO;
import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * 扩展{@link ReactiveMongoTemplate}
 *
 * @author CPoet
 */
public class MongoTemplate extends ReactiveMongoTemplate {

    private final QueryGeneratorFactory queryGeneratorFactory;

    public MongoTemplate(ReactiveMongoDatabaseFactory mongoDatabaseFactory,
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
    public <T> Flux<T> findParam(Object param, Class<T> entityClass) {
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
    public <T> Flux<T> findParam(Object param, Class<T> entityClass, Sort sort) {
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
    public <T> Mono<PageVO<T>> findPageParam(PageParam param, Class<T> entityClass) {
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
    public <T> Mono<PageVO<T>> findPageParam(PageParam param, Class<T> entityClass, Sort sort) {
        Query query = queryGeneratorFactory.getQuery(param);
        if (sort != null) {
            query = query.with(sort);
        }
        Query target = query;
        return find(query, entityClass)
            .collectList()
            .flatMap(list -> {
                if (CollectionUtils.isEmpty(list)) {
                    return Mono.just(PageVO.ok(0L, list, param));
                }
                return count(target.skip(0).limit(0), entityClass).map(total -> PageVO.ok(total, list, param));
            });
    }

    /**
     * 传入查询参数进行查询
     *
     * @param param       查询参数
     * @param entityClass 查询实体
     * @param <T>         实体类型
     * @return 查询结果
     */
    public <T> Mono<T> findOneParam(Object param, Class<T> entityClass) {
        Query query = queryGeneratorFactory.getQuery(param);
        return findOne(query, entityClass);
    }
}
