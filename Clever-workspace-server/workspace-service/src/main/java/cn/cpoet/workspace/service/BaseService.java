package cn.cpoet.workspace.service;

import cn.cpoet.workspace.ibatis.mapper.BaseMapper;

import java.util.List;

/**
 * 基础操作定义
 *
 * @author CPoet
 */
public interface BaseService<T> {

    /**
     * 获取Mapper对象
     *
     * @return mapper对象
     */
    BaseMapper<T> getBaseMapper();

    /**
     * 根据id查询
     *
     * @param id id
     * @return 查询结果
     */
    T getById(Long id);

    /**
     * 根据id查询列表
     *
     * @param ids id列表
     * @return 数据列表
     */
    List<T> listById(List<Long> ids);
}
