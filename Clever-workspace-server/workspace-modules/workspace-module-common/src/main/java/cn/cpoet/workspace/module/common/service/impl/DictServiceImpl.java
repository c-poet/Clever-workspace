package cn.cpoet.workspace.module.common.service.impl;

import cn.cpoet.workspace.api.core.GenMap;
import cn.cpoet.workspace.core.support.EnumDictHandler;
import cn.cpoet.workspace.module.common.service.DictService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public List<GenMap> getDict(String code) {
       return enumDictHandler.getDict(code);
    }
}
