package cn.cpoet.blog.starter.api.common.controller;

import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.starter.api.common.service.PersonService;
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