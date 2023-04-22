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

import java.util.List;

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
    public Group getGroupById(@RequestParam Long id) {
        return groupService.getGroupById(id);
    }

    @GetMapping("/listGroup")
    @Operation(summary = "分页查询分组")
    public PageVO<Group> listGroup(@Validated GroupParam param) {
        return groupService.listGroup(param);
    }

    @GetMapping("/listGroupTree")
    @Operation(summary = "查询分组树")
    public List<GroupNodeVO> listGroupTree(GroupParam param) {
        return groupService.listGroupTree(param);
    }

    @PostMapping("/insertGroup")
    @Operation(summary = "新增分组")
    public Group insertGroup(@RequestBody @Validated(Insert.class) Group group) {
        return groupService.insertGroup(group);
    }

    @PostMapping("/updateGroup")
    @Operation(summary = "更新分组")
    public Group updateGroup(@RequestBody @Validated(Update.class) Group group) {
        return groupService.updateGroup(group);
    }

    @PostMapping("/deleteGroupById")
    @Operation(summary = "删除分组")
    public void deleteGroupById(@RequestBody @Validated IdDTO idDTO) {
         groupService.deleteGroupById(idDTO.getId());
    }

    @PostMapping("/deleteGroupByIds")
    @Operation(summary = "批量删除分组")
    public void deleteGroupByIds(@RequestBody @Validated IdsDTO idsDTO) {
         groupService.deleteGroupByIds(idsDTO.getIds());
    }
}
