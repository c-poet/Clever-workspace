package cn.cpoet.blog.repo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author CPoet
 */
@Configuration
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories("cn.cpoet.blog.repo.repository")
public class MongoConfig {
}
