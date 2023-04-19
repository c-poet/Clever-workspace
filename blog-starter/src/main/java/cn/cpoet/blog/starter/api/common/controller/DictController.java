package cn.cpoet.blog.starter.api.common.controller;

import cn.cpoet.blog.api.core.Dict;
import cn.cpoet.blog.starter.api.common.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author CPoet
 */
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;

    @GetMapping("/getDict")
    @Operation(summary = "查询字典")
    public Mono<Map<String, Dict>> getDict(@RequestParam String code) {
        return dictService.getDict(code);
    }

    @GetMapping("/listDict")
    @Operation(summary = "查询字典值列表")
    public Flux<Dict> listDict(@RequestParam String code) {
        return dictService.listDict(code);
    }
}
