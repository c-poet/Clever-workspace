package cn.cpoet.workspace.module.common.controller;

import cn.cpoet.workspace.api.context.Subject;
import cn.cpoet.workspace.model.domain.User;
import cn.cpoet.workspace.module.common.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CPoet
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/getPersonInfo")
    public User getPersonInfo(Subject subject) {
        return personService.getPersonInfo(subject);
    }
}
