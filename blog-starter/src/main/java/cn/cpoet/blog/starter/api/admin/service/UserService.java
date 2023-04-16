package cn.cpoet.blog.starter.api.admin.service;

import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.model.domain.User;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author CPoet
 */
public interface UserService {
    /**
     * 保存用户
     *
     * @param subject 当前登录主体
     * @param user    用户信息
     * @return 用户信息
     */
    Mono<User> save(Subject subject, User user);

    /**
     * 删除用户
     *
     * @param subject 当前登录主体
     * @param ids     id信息
     * @return 删除结果
     */
    Mono<Void> deleteById(Subject subject, List<Long> ids);
}
