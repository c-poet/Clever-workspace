package cn.cpoet.workspace.starter.api.common.controller;

import cn.cpoet.workspace.api.core.GenMap;
import cn.cpoet.workspace.starter.api.common.service.DictService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author CPoet
 */
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictController {

    private final DictService dictService;

    @GetMapping("/getDict")
    @Operation(summary = "查询字典值列表")
    public List<GenMap> listDict(@RequestParam String code) {
        return dictService.getDict(code);
    }
}
