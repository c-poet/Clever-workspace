package cn.cpoet.blog.starter.api.admin.controller;

import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.starter.api.admin.service.PersonService;
import cn.cpoet.blog.starter.api.admin.vo.MenuNodeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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
    public Flux<MenuNodeVO> listMenu(Subject subject) {
        return personService.listMenu(subject);
    }
}
