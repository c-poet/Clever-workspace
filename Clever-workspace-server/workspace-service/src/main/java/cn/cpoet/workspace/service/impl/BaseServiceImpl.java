package cn.cpoet.workspace.service.impl;

import cn.cpoet.workspace.ibatis.mapper.BaseMapper;
import cn.cpoet.workspace.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;

/**
 * @author CPoet
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    protected BaseMapper<T> baseMapper;

    @Override
    public BaseMapper<T> getBaseMapper() {
        return baseMapper;
    }

    @Override
    public T getById(Long id) {
        return baseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> listById(Collection<Long> ids) {
        return baseMapper.listById(ids);
    }
}
