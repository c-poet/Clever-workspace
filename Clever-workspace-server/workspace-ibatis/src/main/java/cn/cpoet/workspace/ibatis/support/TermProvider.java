package cn.cpoet.workspace.ibatis.support;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

/**
 * @author CPoet
 */
public class TermProvider extends MapperTemplate {

    public TermProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 查询单个数据
     *
     * @param ms {@link MappedStatement}
     * @return sql
     */
    public String findOne(MappedStatement ms) {
        return "";
    }

    /**
     * 查询数据列表
     *
     * @param ms {@link MappedStatement}
     * @return sql
     */
    public String find(MappedStatement ms) {
        return "";
    }
}
