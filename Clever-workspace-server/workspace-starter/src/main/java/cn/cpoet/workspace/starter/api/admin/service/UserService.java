package cn.cpoet.workspace.starter.api.admin.service;

import cn.cpoet.workspace.core.vo.PageVO;
import cn.cpoet.workspace.model.domain.User;
import cn.cpoet.workspace.starter.api.admin.dto.UserDTO;
import cn.cpoet.workspace.starter.api.admin.param.UserParam;
import cn.cpoet.workspace.starter.api.admin.vo.UserVO;

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
    User getUserById(Long userId);

    /**
     * 查询用户
     *
     * @param userParam 查询参数
     * @return 用户列表
     */
    PageVO<UserVO> listUser(UserParam userParam);

    /**
     * 保存用户
     *
     * @param userDTO 用户信息
     * @return 用户信息
     */
    User insertUser(UserDTO userDTO);

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 用户信息
     */
    User updateUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    void deleteUserById(Long id);

    /**
     * 批量删除用户
     *
     * @param ids 用户id列表
     */
    void deleteUserByIds(List<Long> ids);
}
