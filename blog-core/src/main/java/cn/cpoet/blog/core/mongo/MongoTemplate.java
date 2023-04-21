package cn.cpoet.blog.core.mongo;

import cn.cpoet.blog.core.param.PageParam;
import cn.cpoet.blog.core.vo.PageVO;
import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

/**
 * 扩展{@link ReactiveMongoTemplate}
 *
 * @author CPoet
 */
public class MongoTemplate extends ReactiveMongoTemplate {
    public MongoTemplate(MongoClient mongoClient, String databaseName) {
        super(mongoClient, databaseName);
    }

    public MongoTemplate(ReactiveMongoDatabaseFactory mongoDatabaseFactory) {
        super(mongoDatabaseFactory);
    }

    public MongoTemplate(ReactiveMongoDatabaseFactory mongoDatabaseFactory, MongoConverter mongoConverter) {
        super(mongoDatabaseFactory, mongoConverter);
    }

    public MongoTemplate(ReactiveMongoDatabaseFactory mongoDatabaseFactory, MongoConverter mongoConverter, Consumer<Throwable> subscriptionExceptionHandler) {
        super(mongoDatabaseFactory, mongoConverter, subscriptionExceptionHandler);
    }

    /**
     * 分页查询数据
     *
     * @param query       查询条件
     * @param param       查询参数
     * @param entityClass 查询的实体
     * @param <T>         实体类型
     * @return 查询结果
     */
    public <T> Mono<PageVO<T>> find(Query query, PageParam param, Class<T> entityClass) {
        return count(query, entityClass)
            .flatMap(total -> {
                if (total == 0) {
                    return Mono.empty();
                }
                return find(query.with(param.toPageRequest()), entityClass)
                    .collectList()
                    .map(data -> PageVO.ok(total, data, param));
            });
    }
}
