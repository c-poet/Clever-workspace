package cn.cpoet.workspace.ibatis.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Mapper基类
 *
 * @author CPoet
 */
@RegisterMapper
public interface BaseMapper<T> extends tk.mybatis.mapper.common.BaseMapper<T>,
    MySqlMapper<T>,
    IdListMapper<T, Long>,
    TermMapper<T> {
}

