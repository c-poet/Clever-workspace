package cn.cpoet.workspace.module.admin.service.impl;

import cn.cpoet.workspace.api.exception.BusException;
import cn.cpoet.workspace.core.component.UserPassCryptoStrategy;
import cn.cpoet.workspace.core.vo.PageVO;
import cn.cpoet.workspace.model.domain.User;
import cn.cpoet.workspace.module.admin.dto.UserDTO;
import cn.cpoet.workspace.module.admin.param.UserParam;
import cn.cpoet.workspace.module.admin.service.UserService;
import cn.cpoet.workspace.module.admin.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserPassCryptoStrategy userPassCryptoStrategy;

    @Override
    public User getUserById(Long userId) {
//        return userRepository.findById(userId).orElse(null);
        return null;
    }

    @Override
    public PageVO<UserVO> listUser(UserParam userParam) {
//        return  mongoTemplate
//            .findPageParam(userParam, User.class)
//            .map(UserVO::of)
//            .consume(this::fillExtInfo);
        return null;
    }

    private void fillExtInfo(List<UserVO> userVOList) {
//        if (!CollectionUtils.isEmpty(userVOList)) {
//            List<Long> userIds = new ArrayList<>(userVOList.size());
//            List<Long> groupIds = new ArrayList<>(userVOList.size());
//            for (UserVO userVO : userVOList) {
//                if (userVO.getGroupId() != null && !Objects.equals(userVO.getGroupId(), SystemConst.DEFAULT_ID)) {
//                    groupIds.add(userVO.getGroupId());
//                }
//                userIds.add(userVO.getId());
//            }
//            List<Group> groups = CollectionUtils.isEmpty(groupIds) ? Collections.emptyList() : groupRepository.findAllById(groupIds);
//            Map<Long, Group> groupMap;
//            if (CollectionUtils.isEmpty(groups)) {
//                groupMap = Collections.emptyMap();
//            } else {
//                groupMap = groups.stream().collect(Collectors.toMap(Group::getId, Function.identity()));
//            }
//            List<LoginLog> loginLogs = loginLogService.findLastLog(userIds);
//            Map<Long, LoginLog> loginLogMap;
//            if (CollectionUtils.isEmpty(loginLogs)) {
//                loginLogMap = Collections.emptyMap();
//            } else {
//               loginLogMap = loginLogs.stream().collect(Collectors.toMap(LoginLog::getUserId, Function.identity()));
//            }
//            for (UserVO userVO : userVOList) {
//                Group group = groupMap.get(userVO.getGroupId());
//                if (group != null) {
//                    userVO.setGroupCode(group.getCode());
//                    userVO.setGroupName(group.getName());
//                }
//                LoginLog loginLog = loginLogMap.get(userVO.getId());
//                if (loginLog != null) {
//                    userVO.setLastLoginIp(loginLog.getIpAddress());
//                    userVO.setLastLoginTime(loginLog.getLoginTime());
//                }
//            }
//        }
        return;
    }

    @Override
    public User insertUser(UserDTO userDTO) {
//        if (!Objects.equals(userDTO.getUserPass(), userDTO.getConfirmPass())) {
//            throw new BusException("两次输入的密码不一致");
//        }
//        userDTO.setSalt(UUIDUtil.random());
//        userDTO.setPassword(userPassCryptoStrategy.encode(userDTO, userDTO.getUserPass()));
//        userDTO.setBuildIn(Boolean.FALSE);
//        return userRepository.insert(userDTO);
        return null;
    }

    @Override
    public User updateUser(User user) {
//        User old = userRepository.findById(user.getId()).orElseThrow(() -> new BusException("更新的用户不存在"));
//        return userRepository.save(BeanUtil.copyEditableProperties(user, old));
        return null;
    }

    @Override
    public void deleteUserById(Long id) {
//        userRepository.findById(id).ifPresent(user -> {
//            checkDeleteUser(user);
//            userRepository.delete(user);
//        });
        return;
    }

    @Override
    public void deleteUserByIds(List<Long> ids) {
//        List<User> users = userRepository.findAllById(ids);
//        if (!CollectionUtils.isEmpty(users)) {
//            for (User user : users) {
//                checkDeleteUser(user);
//            }
//            userRepository.deleteAll(users);
//        }
        return;
    }

    private void checkDeleteUser(User user) {
        if (Boolean.TRUE.equals(user.getBuildIn())) {
            throw new BusException("内置用户不允许删除");
        }
    }
}
