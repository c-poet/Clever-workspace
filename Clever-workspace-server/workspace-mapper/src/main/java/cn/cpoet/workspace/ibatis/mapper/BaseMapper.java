package cn.cpoet.workspace.ibatis.mapper;

import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.Collection;
import java.util.List;

/**
 * Mapper基类
 *
 * @author CPoet
 */
@RegisterMapper
public interface BaseMapper<T> extends tk.mybatis.mapper.common.BaseMapper<T>, MySqlMapper<T>,
    EntityMapper<T>, TermMapper<T>{
    /**
     * 根据id查询数据列表
     *
     * @param ids id列表
     * @return 数据列表
     */
    @SelectProvider()
    List<T> listById(Collection<Long> ids);
}

