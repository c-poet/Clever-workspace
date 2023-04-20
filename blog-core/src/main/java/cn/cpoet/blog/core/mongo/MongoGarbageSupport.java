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
import java.util.List;
import java.util.stream.Collectors;

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
     * @param event     删除事件
     * @param documents 删除的文档列表
     */
    public void saveGarbage(BeforeDeleteEvent<Entity<Long>> event, List<GenMap> documents) {
        threadPoolTaskExecutor.execute(() -> {
            List<Garbage> garbageList = genGarbage(event, documents);
            String collectionName = getCurGarbageCollectionName();
            mongoTemplate.insert(garbageList, collectionName)
                .count()
                .doOnSuccess(size -> log.info("保存文档[{}]删除记录成功，条数：{}", event.getCollectionName(), size))
                .doOnError(error -> log.warn("保存文档[{}]删除记录失败:{}", event.getCollectionName(), error.getMessage(), error))
                .block();
        });
    }

    private String getCurGarbageCollectionName() {
        String collectionName = mongoTemplate.getCollectionName(Garbage.class);
        // 默认按年度记录
        return collectionName + "_" + Year.now().getValue();
    }

    private List<Garbage> genGarbage(BeforeDeleteEvent<Entity<Long>> event, List<GenMap> documents) {
        return documents.stream().map(document -> genGarbage(event, document)).collect(Collectors.toList());
    }

    private Garbage genGarbage(BeforeDeleteEvent<Entity<Long>> event, GenMap document) {
        Garbage garbage = new Garbage();
        garbage.setName(event.getCollectionName());
        garbage.setDocument(document);
        return garbage;
    }
}
