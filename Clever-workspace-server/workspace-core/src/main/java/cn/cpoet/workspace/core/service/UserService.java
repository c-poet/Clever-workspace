package cn.cpoet.workspace.core.service;

import java.util.List;

/**
 * @author CPoet
 */
public interface UserService {
    /**
     * 判断是否存在用户组
     *
     * @param groupIds 用户组id列表
     * @return 是否存在用户组
     */
    boolean existsByGroups(List<Long> groupIds);
}
