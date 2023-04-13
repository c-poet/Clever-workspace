package cn.cpoet.blog.repo.support;

import cn.cpoet.blog.api.core.GenMap;
import cn.cpoet.blog.model.base.Entity;
import cn.cpoet.blog.model.domain.Garbage;
import cn.cpoet.blog.repo.repository.GarbageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * @author CPoet
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GarbageSupport {

    private final GarbageRepository garbageRepository;
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
            garbageRepository.save(garbage)
                .doOnSuccess(gb -> log.info("保存删除记录成功:[{},{}]", gb.getEntityClass(), gb.getDocumentId()))
                .doOnError(error -> log.warn("保存删除记录失败:{}", error.getMessage(), error))
                .block();
        });
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
        garbage.setEntityClass(entity.getClass().getName());
        garbage.setEntity(GenMap.of(entity));
        return garbage;
    }
}
