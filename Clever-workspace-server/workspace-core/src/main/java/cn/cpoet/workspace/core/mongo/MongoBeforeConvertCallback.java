package cn.cpoet.workspace.core.mongo;

import cn.cpoet.workspace.api.core.IdGenerator;
import cn.cpoet.workspace.model.base.BaseRcEntity;
import cn.cpoet.workspace.model.base.Entity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

/**
 * @author CPoet
 */
@Component
@RequiredArgsConstructor
public class MongoBeforeConvertCallback implements BeforeConvertCallback<Entity<Long>> {

    private final IdGenerator<Long> idGenerator;
    private final MongoMappingContext mongoMappingContext;

    @Override
    public Entity<Long> onBeforeConvert(Entity<Long> entity, String collection) {
        return handleEntity(entity);
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
