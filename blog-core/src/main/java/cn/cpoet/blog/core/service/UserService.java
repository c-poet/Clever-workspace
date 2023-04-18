package cn.cpoet.blog.core.service;

import reactor.core.publisher.Mono;

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
    Mono<Boolean> existsByGroups(List<Long> groupIds);
}
