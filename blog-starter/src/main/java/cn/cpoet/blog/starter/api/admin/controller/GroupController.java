package cn.cpoet.blog.starter.api.admin.controller;

import cn.cpoet.blog.api.validation.Insert;
import cn.cpoet.blog.api.validation.Update;
import cn.cpoet.blog.core.dto.IdDTO;
import cn.cpoet.blog.core.dto.IdsDTO;
import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.Group;
import cn.cpoet.blog.starter.api.admin.param.GroupParam;
import cn.cpoet.blog.starter.api.admin.service.GroupService;
import cn.cpoet.blog.starter.api.admin.vo.GroupNodeVO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/getGroupById")
    @Operation(summary = "根据id查询分组")
    public Mono<Group> getGroupById(@RequestParam Long id) {
        return groupService.getGroupById(id);
    }

    @GetMapping("/listGroup")
    @Operation(summary = "分页查询分组")
    public Mono<PageVO<Group>> listGroup(@RequestBody @Validated GroupParam groupParam) {
        return groupService.listGroup(groupParam);
    }

    @GetMapping("/listGroupTree")
    @Operation(summary = "查询分组树")
    public Flux<GroupNodeVO> listGroupTree() {
        return groupService.listGroupTree();
    }

    @PostMapping("/insertGroup")
    @Operation(summary = "新增分组")
    public Mono<Group> insertGroup(@RequestBody @Validated(Insert.class) Group group) {
        return groupService.insertGroup(group);
    }

    @PostMapping("/updateGroup")
    @Operation(summary = "更新分组")
    public Mono<Group> updateGroup(@RequestBody @Validated(Update.class) Group group) {
        return groupService.updateGroup(group);
    }

    @PostMapping("/deleteGroupById")
    @Operation(summary = "删除分组")
    public Mono<Void> deleteGroupById(@RequestBody @Validated IdDTO idDTO) {
        return groupService.deleteGroupById(idDTO.getId());
    }

    @PostMapping("/deleteGroupByIds")
    @Operation(summary = "批量删除分组")
    public Mono<Void> deleteGroupByIds(@RequestBody @Validated IdsDTO idsDTO) {
        return groupService.deleteGroupByIds(idsDTO.getIds());
    }
}
