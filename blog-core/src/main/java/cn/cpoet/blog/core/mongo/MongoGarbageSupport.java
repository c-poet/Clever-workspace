package cn.cpoet.blog.core.mongo;

import cn.cpoet.blog.api.core.GenMap;
import cn.cpoet.blog.model.base.Entity;
import cn.cpoet.blog.model.domain.Garbage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Year;

/**
 * @author CPoet
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MongoGarbageSupport {

    private final ReactiveMongoTemplate mongoTemplate;
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 异步保存回收记录
     *
     * @param event  删除事件
     * @param entity 删除的实体
     */
    public void saveGarbage(BeforeDeleteEvent<Entity<Long>> event, Entity<Long> entity) {
        threadPoolTaskExecutor.execute(() -> {
            Garbage garbage = genGarbage(event, entity);
            String collectionName = getCurGarbageCollectionName();
            mongoTemplate.insert(garbage, collectionName)
                .doOnSuccess(gb -> log.info("保存删除记录成功:[{},{}]", gb.getCollectionName(), gb.getDocumentId()))
                .doOnError(error -> log.warn("保存删除记录失败:{}", error.getMessage(), error))
                .block();
        });
    }

    /**
     * 获取当前使用的集合名称
     *
     * @return 集合名称
     */
    private String getCurGarbageCollectionName() {
        String collectionName = mongoTemplate.getCollectionName(Garbage.class);
        // 默认按年度记录
        return collectionName + "_" + Year.now().getValue();
    }

    /**
     * 生成记录
     *
     * @param event  事件
     * @param entity 实体
     */
    private Garbage genGarbage(BeforeDeleteEvent<Entity<Long>> event, Entity<Long> entity) {
        Garbage garbage = new Garbage();
        garbage.setDocumentId(entity.getId());
        garbage.setCollectionName(event.getCollectionName());
        garbage.setDocument(GenMap.of(entity));
        return garbage;
    }
}
