package cn.cpoet.blog.starter.api.common.service;

import cn.cpoet.blog.api.core.GenMap;
import reactor.core.publisher.Flux;

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
    Flux<GenMap> getDict(String code);
}
