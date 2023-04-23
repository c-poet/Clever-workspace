package cn.cpoet.workspace.repo.repository;

import cn.cpoet.workspace.model.domain.User;
import cn.cpoet.workspace.repo.base.BaseRepository;
import org.springframework.stereotype.Repository;

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
    User findByUsername(String username);
}
