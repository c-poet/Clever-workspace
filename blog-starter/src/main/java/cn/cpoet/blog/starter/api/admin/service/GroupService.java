package cn.cpoet.blog.starter.api.admin.service;

import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.Group;
import cn.cpoet.blog.starter.api.admin.param.GroupParam;
import cn.cpoet.blog.starter.api.admin.vo.GroupNodeVO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author CPoet
 */
public interface GroupService {
    /**
     * 根据id查询分组
     *
     * @param groupId 用户组id
     * @return 分组信息
     */
    Mono<Group> getGroupById(Long groupId);

    /**
     * 分页查询分组
     *
     * @param param 分组查询参数
     * @return 查询结果
     */
    Mono<PageVO<Group>> listGroup(GroupParam param);

    /**
     * 查询用户分组树
     *
     * @param param 查询参数
     * @return 用户分组树
     */
    Flux<GroupNodeVO> listGroupTree(GroupParam param);

    /**
     * 新增分组
     *
     * @param group 分组
     * @return 分组
     */
    Mono<Group> insertGroup(Group group);

    /**
     * 更新分组
     *
     * @param group 分组
     * @return 分组
     */
    Mono<Group> updateGroup(Group group);

    /**
     * 根据id删除分组
     *
     * @param id 分组id
     * @return 删除结果
     */
    Mono<Void> deleteGroupById(Long id);

    /**
     * 批量删除分组
     *
     * @param ids id列表
     * @return 删除结果
     */
    Mono<Void> deleteGroupByIds(List<Long> ids);
}
