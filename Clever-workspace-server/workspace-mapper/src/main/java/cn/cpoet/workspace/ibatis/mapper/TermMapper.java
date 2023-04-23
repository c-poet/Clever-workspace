package cn.cpoet.workspace.ibatis.mapper;

import cn.cpoet.workspace.ibatis.support.TermProvider;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * 动态查询参数解析
 *
 * @author CPoet
 */
@RegisterMapper
public interface TermMapper<T> {
    /**
     * 查询单个值
     *
     * @param bean 查询条件
     * @return 查询结果
     */
    @SelectProvider(value = TermProvider.class)
    T findOne(Object bean);

    /**
     * 查询列表
     *
     * @param bean 查询条件
     * @return 查询结果
     */
    @SelectProvider(value = TermProvider.class)
    List<T> find(Object bean);
}
