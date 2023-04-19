package cn.cpoet.blog.starter.api.common.service.impl;

import cn.cpoet.blog.api.core.Dict;
import cn.cpoet.blog.core.support.EnumDictHandler;
import cn.cpoet.blog.starter.api.common.service.DictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictServiceImpl implements DictService {

    private final EnumDictHandler enumDictHandler;

    @Override
    public Mono<Map<String, Dict>> getDict(String code) {
        return listDict(code).collectMap(Dict::getCode, Function.identity());
    }

    @Override
    public Flux<Dict> listDict(String code) {
        List<Dict> dictList = enumDictHandler.getDict(code);
        return CollectionUtils.isEmpty(dictList) ? Flux.empty() : Flux.fromIterable(dictList);
    }
}
