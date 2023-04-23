package cn.cpoet.workspace.starter.api.common.service;

import cn.cpoet.workspace.api.core.GenMap;

import java.util.List;

/**
 * @author CPoet
 */
public interface DictService {

    /**
     * 查询字典列表
     *
     * @param code 字典编码
     * @return 字典列表
     */
    List<GenMap> getDict(String code);
}
