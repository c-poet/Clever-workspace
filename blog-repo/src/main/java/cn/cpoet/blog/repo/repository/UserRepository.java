package cn.cpoet.blog.repo.repository;

import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.repo.base.BaseRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
@Repository
public interface UserRepository extends BaseRepository<User> {
    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 用户信息
     */
    Mono<User> findByUsername(String username);
}
