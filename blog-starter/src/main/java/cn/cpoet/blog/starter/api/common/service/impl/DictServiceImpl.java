package cn.cpoet.blog.starter.api.common.service.impl;

import cn.cpoet.blog.api.core.GenMap;
import cn.cpoet.blog.core.support.EnumDictHandler;
import cn.cpoet.blog.starter.api.common.service.DictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author CPoet
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DictServiceImpl implements DictService {

    private final EnumDictHandler enumDictHandler;

    @Override
    public Flux<GenMap> getDict(String code) {
        List<GenMap> dictList = enumDictHandler.getDict(code);
        return CollectionUtils.isEmpty(dictList) ? Flux.empty() : Flux.fromIterable(dictList);
    }
}
