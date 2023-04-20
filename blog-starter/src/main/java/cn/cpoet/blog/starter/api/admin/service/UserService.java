package cn.cpoet.blog.starter.api.admin.service;

import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.starter.api.admin.dto.UserDTO;
import cn.cpoet.blog.starter.api.admin.param.UserParam;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author CPoet
 */
public interface UserService {
    /**
     * 根据id查询用户
     *
     * @param userId 用户id
     * @return 用户信息
     */
    Mono<User> getUserById(Long userId);

    /**
     * 查询用户
     *
     * @param userParam 查询参数
     * @return 用户列表
     */
    Mono<PageVO<User>> listUser(UserParam userParam);

    /**
     * 保存用户
     *
     * @param userDTO 用户信息
     * @return 用户信息
     */
    Mono<User> insertUser(UserDTO userDTO);

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 用户信息
     */
    Mono<User> updateUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 删除结果
     */
    Mono<Void> deleteUserById(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 用户id列表
     * @return 删除结果
     */
    Mono<Void> deleteUserByIds(List<Long> ids);
}
