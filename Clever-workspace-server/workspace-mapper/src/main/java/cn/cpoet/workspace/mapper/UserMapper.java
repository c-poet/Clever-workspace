package cn.cpoet.workspace.mapper;

import cn.cpoet.workspace.model.domain.User;
import cn.cpoet.workspace.mapper.base.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author CPoet
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);
}
