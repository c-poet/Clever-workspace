package cn.cpoet.blog.starter.api.common.service;

import cn.cpoet.blog.api.core.Dict;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author CPoet
 */
public interface DictService {
    /**
     * 查询字典
     *
     * @param code 字典编码
     * @return 字典
     */
    Mono<Map<String, Dict>> getDict(String code);

    /**
     * 查询字典列表
     *
     * @param code 字典编码
     * @return 字典列表
     */
    Flux<Dict> listDict(String code);
}
