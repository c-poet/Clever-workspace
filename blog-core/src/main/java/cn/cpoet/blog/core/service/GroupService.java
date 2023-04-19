package cn.cpoet.blog.core.service;

import cn.cpoet.blog.model.domain.Group;
import reactor.core.publisher.Flux;

/**
 * @author CPoet
 */
public interface GroupService {
    /**
     * 查询所有用户组并排序
     *
     * @return 用户组列表
     */
    Flux<Group> listByOrder();
}
