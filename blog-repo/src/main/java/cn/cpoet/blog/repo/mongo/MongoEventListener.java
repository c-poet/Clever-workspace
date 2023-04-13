package cn.cpoet.blog.repo.mongo;

import cn.cpoet.blog.api.core.GenMap;
import cn.cpoet.blog.api.exception.AppException;
import cn.cpoet.blog.model.base.Entity;
import cn.cpoet.blog.model.domain.Garbage;
import cn.cpoet.blog.repo.repository.GarbageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author CPoet
 */
@Component
@RequiredArgsConstructor
public class MongoEventListener extends AbstractMongoEventListener<Entity<Long>> {

    private final ObjectMapper objectMapper;
    private final GarbageRepository garbageRepository;
    private final ReactiveMongoTemplate mongoTemplate;
    private final MongoMappingContext mongoMappingContext;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Entity<Long>> event) {
        Class<Entity<Long>> entityClass = event.getType();
        if (entityClass != null && !Garbage.class.isAssignableFrom(entityClass) && !CollectionUtils.isEmpty(event.getDocument())) {
            MongoPersistentEntity<?> entity = mongoMappingContext.getRequiredPersistentEntity(entityClass);
            Document document = event.getDocument();
            String fieldName = entity.getRequiredIdProperty().getFieldName();
            mongoTemplate
                .findById(document.getLong(fieldName), entityClass)
                .subscribe(e -> garbageRepository.save(genGarbage(event, e)).block());
        }
    }

    /**
     * 保存记录
     *
     * @param event  事件
     * @param entity 实体
     */
    private Garbage genGarbage(BeforeDeleteEvent<Entity<Long>> event, Entity<Long> entity) {
        try {
            Garbage garbage = new Garbage();
            garbage.setDocumentId(entity.getId());
            garbage.setCollectionName(event.getCollectionName());
            garbage.setEntityClass(entity.getClass().getName());
            garbage.setEntity(GenMap.of(entity));
            return garbage;
        } catch (Exception e) {
            throw new AppException("保存删除记录失败", e);
        }
    }
}
