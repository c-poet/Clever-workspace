package cn.cpoet.blog.repo.base;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 基础Repository
 *
 * @author CPoet
 */
@NoRepositoryBean
public interface BaseRepository<T> extends ReactiveMongoRepository<T, Long> {
}
