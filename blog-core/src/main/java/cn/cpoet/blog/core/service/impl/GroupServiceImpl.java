package cn.cpoet.blog.core.service.impl;

import cn.cpoet.blog.core.service.GroupService;
import cn.cpoet.blog.repo.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
}
