package cn.cpoet.blog.core.mongo;

import cn.cpoet.blog.api.core.GenMap;
import cn.cpoet.blog.model.base.Entity;
import cn.cpoet.blog.model.domain.Garbage;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author CPoet
 */
@Component
@RequiredArgsConstructor
public class MongoEventListener extends AbstractMongoEventListener<Entity<Long>> {

    private final ReactiveMongoTemplate mongoTemplate;
    private final MongoGarbageSupport mongoGarbageSupport;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Entity<Long>> event) {
        Class<Entity<Long>> entityClass = event.getType();
        if (entityClass != null && !Garbage.class.isAssignableFrom(entityClass) && !CollectionUtils.isEmpty(event.getDocument())) {
            Document document = event.getDocument();
            mongoTemplate.execute(entityClass, db -> db.find(document.toBsonDocument()))
                .map(GenMap::ofMap)
                .collectList()
                .subscribe(docs -> mongoGarbageSupport.saveGarbage(event, docs));
        }
    }
}
