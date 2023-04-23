package cn.cpoet.workspace.mapper;

import cn.cpoet.workspace.ibatis.mapper.BaseMapper;
import cn.cpoet.workspace.model.domain.User;

/**
 * @author CPoet
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);
}
