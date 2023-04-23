package cn.cpoet.workspace.module.admin.service.impl;

import cn.cpoet.workspace.api.constant.SystemConst;
import cn.cpoet.workspace.core.exception.BusException;
import cn.cpoet.workspace.core.mongo.MongoTemplate;
import cn.cpoet.workspace.core.service.UserService;
import cn.cpoet.workspace.core.util.BeanUtil;
import cn.cpoet.workspace.core.vo.PageVO;
import cn.cpoet.workspace.model.domain.Group;
import cn.cpoet.workspace.repo.repository.GroupRepository;
import cn.cpoet.workspace.module.admin.param.GroupParam;
import cn.cpoet.workspace.module.admin.service.GroupService;
import cn.cpoet.workspace.module.admin.vo.GroupNodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final UserService userService;
    private final MongoTemplate mongoTemplate;
    private final GroupRepository groupRepository;

    @Override
    public Group getGroupById(Long groupId) {
        return groupRepository
            .findById(groupId)
            .orElse(null);
    }

    @Override
    public PageVO<Group> listGroup(GroupParam param) {
        return mongoTemplate.findPageParam(param, Group.class);
    }

    @Override
    public List<GroupNodeVO> listGroupTree(GroupParam param) {
        List<Group> groups = mongoTemplate.findParam(param, Group.class, Sort.by(Group.Fields.order));
        if (CollectionUtils.isEmpty(groups)) {
            return Collections.emptyList();
        }
        Map<Long, List<GroupNodeVO>> mapping = new HashMap<>(1 << 4);
        for (Group group : groups) {
            mapping.computeIfAbsent(group.getParentId(), k -> new ArrayList<>()).add(GroupNodeVO.of(group));
        }
        for (List<GroupNodeVO> children : mapping.values()) {
            for (GroupNodeVO child : children) {
                List<GroupNodeVO> curChildren = mapping.get(child.getId());
                if (!CollectionUtils.isEmpty(curChildren)) {
                    child.setChildren(curChildren);
                }
            }
        }
        return mapping.getOrDefault(SystemConst.DEFAULT_PID, Collections.emptyList());
    }


    @Override
    public Group insertGroup(Group group) {
        group.setBuildIn(Boolean.FALSE);
        return groupRepository.insert(group);
    }

    @Override
    public Group updateGroup(Group group) {
        Group old = groupRepository.findById(group.getId()).orElseThrow(() -> new BusException("分组不存在"));
        return groupRepository.save(BeanUtil.copyEditableProperties(group, old));
    }

    @Override
    public void deleteGroupById(Long id) {
        groupRepository.findById(id).ifPresent(group -> {
                checkDeleteGroup(group);
                groupRepository.delete(group);
            });
    }

    @Override
    public void deleteGroupByIds(List<Long> ids) {
        List<Group> groups = groupRepository.findAllById(ids);
        if (!CollectionUtils.isEmpty(groups)) {
            for (Group group : groups) {
                checkDeleteGroup(group);
            }
            groupRepository.deleteAll(groups);
        }

    }

    private void checkDeleteGroup(Group group) {
        if (Boolean.TRUE.equals(group.getBuildIn())) {
            throw new BusException("内置用户组，拒绝删除");
        }
        if (userService.existsByGroups(Collections.singletonList(group.getId()))) {
            throw new BusException("分组下存在用户，拒绝删除");
        }
    }
}
