package cn.cpoet.workspace.module.admin.controller;

import cn.cpoet.workspace.api.context.Subject;
import cn.cpoet.workspace.module.admin.service.PersonService;
import cn.cpoet.workspace.module.admin.vo.MenuNodeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author CPoet
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
@Tag(name = "用户信息")
public class PersonController {
    private final PersonService personService;

    @GetMapping("/listMenu")
    @Operation(summary = "获取菜单列表")
    public List<MenuNodeVO> listMenu(Subject subject) {
        return personService.listMenu(subject);
    }
}
