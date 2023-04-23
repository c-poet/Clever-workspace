package cn.cpoet.workspace.core.mongo;

import cn.cpoet.workspace.api.core.GenMap;
import cn.cpoet.workspace.model.base.Entity;
import cn.cpoet.workspace.model.domain.Garbage;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CPoet
 */
@Component
@RequiredArgsConstructor
public class MongoEventListener extends AbstractMongoEventListener<Entity<Long>> {

    private final MongoTemplate mongoTemplate;
    private final MongoGarbageSupport mongoGarbageSupport;

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<Entity<Long>> event) {
        Class<Entity<Long>> entityClass = event.getType();
        if (entityClass != null && !Garbage.class.isAssignableFrom(entityClass) && !CollectionUtils.isEmpty(event.getDocument())) {
            Document document = event.getDocument();
            FindIterable<Document> documents = mongoTemplate.execute(entityClass, db -> db.find(document.toBsonDocument()));
            if (documents != null) {
                MongoCursor<Document> it = documents.iterator();
                List<GenMap> genMaps = new ArrayList<>();
                while (it.hasNext()) {
                    genMaps.add(GenMap.of(it.next()));
                }
                if (!CollectionUtils.isEmpty(genMaps)) {
                    mongoGarbageSupport.saveGarbage(event, genMaps);
                }
            }
        }
    }
}
