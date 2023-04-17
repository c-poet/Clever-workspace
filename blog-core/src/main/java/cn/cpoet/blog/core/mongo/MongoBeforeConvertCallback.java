package cn.cpoet.blog.core.mongo;

import cn.cpoet.blog.api.core.IdGenerator;
import cn.cpoet.blog.model.base.BaseRcEntity;
import cn.cpoet.blog.model.base.Entity;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.ReactiveBeforeConvertCallback;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
@Component
@RequiredArgsConstructor
public class MongoBeforeConvertCallback implements ReactiveBeforeConvertCallback<Entity<Long>> {

    private final IdGenerator<Long> idGenerator;
    private final MongoMappingContext mongoMappingContext;

    @Override
    public Publisher<Entity<Long>> onBeforeConvert(Entity<Long> entity, String collection) {
        return Mono.fromSupplier(() -> handleEntity(entity));
    }

    /**
     * 填充实体值
     *
     * @param entity 实体
     * @return 填充实体
     */
    private Entity<Long> handleEntity(Entity<Long> entity) {
        if (entity instanceof BaseRcEntity) {
            BaseRcEntity baseRcEntity = (BaseRcEntity) entity;
            boolean isNew = mongoMappingContext.getRequiredPersistentEntity(entity.getClass()).isNew(entity);
            if (isNew) {
                baseRcEntity.setId(idGenerator.nextId());
            }
        }
        return entity;
    }
}
