package cn.cpoet.blog.repo.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author CPoet
 */
@ComponentScan("cn.cpoet.blog.repo")
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories("cn.cpoet.blog.repo.repository")
public class RepoConfig {
}
