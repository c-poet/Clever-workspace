package cn.cpoet.blog.repo.base;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * 基础Repository
 *
 * @author CPoet
 */
@NoRepositoryBean
public interface BaseRepository<T> extends MongoRepository<T, Long> {
    /**
     * 根据id查询
     *
     * @param ids must not be {@literal null} nor contain any {@literal null} values.
     * @return 查询结果列表
     */
    List<T> findAllById(Iterable<Long> ids);
}
