package cn.cpoet.blog.starter.api.admin.service.impl;

import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.core.component.UserPassCryptoStrategy;
import cn.cpoet.blog.core.exception.BusException;
import cn.cpoet.blog.core.mongo.MongoTemplate;
import cn.cpoet.blog.core.service.LoginLogService;
import cn.cpoet.blog.core.util.BeanUtil;
import cn.cpoet.blog.core.util.UUIDUtil;
import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.Group;
import cn.cpoet.blog.model.domain.LoginLog;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.repo.repository.GroupRepository;
import cn.cpoet.blog.repo.repository.UserRepository;
import cn.cpoet.blog.starter.api.admin.dto.UserDTO;
import cn.cpoet.blog.starter.api.admin.param.UserParam;
import cn.cpoet.blog.starter.api.admin.service.UserService;
import cn.cpoet.blog.starter.api.admin.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final LoginLogService loginLogService;
    private final UserPassCryptoStrategy userPassCryptoStrategy;

    @Override
    public Mono<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Mono<PageVO<UserVO>> listUser(UserParam userParam) {
        return mongoTemplate.findPageParam(userParam, User.class)
            .map(page -> page.map(UserVO::of))
            .flatMap(this::fillGroupInfo)
            .flatMap(this::fillLoginInfo);
    }

    private Mono<PageVO<UserVO>> fillLoginInfo(PageVO<UserVO> page) {
        if (page.isEmpty()) {
            return Mono.just(page);
        }
        List<UserVO> list = page.getData();
        List<Long> userIds = list.stream().map(UserVO::getId).collect(Collectors.toList());
        return loginLogService
            .findLastLog(userIds)
            .collectMap(LoginLog::getUserId, Function.identity())
            .doOnSuccess(map -> {
                for (UserVO userVO : list) {
                    LoginLog loginLog = map.get(userVO.getId());
                    if (loginLog != null) {
                        userVO.setLastLoginIp(loginLog.getIpAddress());
                        userVO.setLastLoginTime(loginLog.getLoginTime());
                    }
                }
            })
            .thenReturn(page);
    }

    private Mono<PageVO<UserVO>> fillGroupInfo(PageVO<UserVO> page) {
        if (page.isEmpty()) {
            return Mono.just(page);
        }
        List<UserVO> list = page.getData();
        List<Long> groupIds = list.stream().map(UserVO::getGroupId)
            .filter(Objects::nonNull)
            .filter(groupId -> !Objects.equals(groupId, SystemConst.DEFAULT_ID))
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(groupIds)) {
            return Mono.just(page);
        }
        return groupRepository.findAllById(groupIds)
            .collectMap(Group::getId, Function.identity())
            .doOnSuccess(map -> {
                for (UserVO item : list) {
                    Group group = map.get(item.getGroupId());
                    if (group != null) {
                        item.setGroupName(group.getName());
                    }
                }
            })
            .thenReturn(page);
    }

    @Override
    public Mono<User> insertUser(UserDTO userDTO) {
        if (!Objects.equals(userDTO.getUserPass(), userDTO.getConfirmPass())) {
            throw new BusException("两次输入的密码不一致");
        }
        userDTO.setSalt(UUIDUtil.random());
        userDTO.setPassword(userPassCryptoStrategy.encode(userDTO, userDTO.getUserPass()));
        userDTO.setBuildIn(Boolean.FALSE);
        return userRepository.insert(userDTO);
    }

    @Override
    public Mono<User> updateUser(User user) {
        return userRepository.findById(user.getId())
            .map(old -> BeanUtil.copyEditableProperties(user, old))
            .flatMap(userRepository::save);
    }

    @Override
    public Mono<Void> deleteUserById(Long id) {
        return userRepository
            .findById(id)
            .doOnSuccess(user -> {
                if (Boolean.TRUE.equals(user.getBuildIn())) {
                    throw new BusException("内置用户不允许删除");
                }
            })
            .flatMap(userRepository::delete);
    }

    @Override
    public Mono<Void> deleteUserByIds(List<Long> ids) {
        return userRepository
            .findAllById(ids)
            .collectList()
            .doOnSuccess(users -> {
                for (User user : users) {
                    if (Boolean.TRUE.equals(user.getBuildIn())) {
                        throw new BusException("内置用户不允许删除");
                    }
                }
            })
            .flatMap(userRepository::deleteAll);
    }
}
