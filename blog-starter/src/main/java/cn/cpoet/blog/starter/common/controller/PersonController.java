package cn.cpoet.blog.starter.common.controller;

import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.starter.common.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    @GetMapping("/getPersonInfo")
    public Mono<User> getPersonInfo(Subject subject) {
        return personService.getPersonInfo(subject);
    }
}
