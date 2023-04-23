package cn.cpoet.workspace.mapper.base;


import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author CPoet
 */
@RegisterMapper
public interface BaseMapper<T> extends tk.mybatis.mapper.common.BaseMapper<T>, MySqlMapper<T> {
    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    default Class<T> getEntityClass() {
        return EntityUtil.getEntityByMapper(this);
    }

    /**
     * 实例化一个实体对象
     *
     * @return 实体对象
     */
    default T newPureEntity() {
        return EntityUtil.newPureEntity(getEntityClass());
    }

    /**
     * 实例化一个实体对象
     *
     * @return 实例化实体对象
     */
    default T newEntity() {
        return EntityUtil.newEntity(getEntityClass());
    }

    /**
     * 填充实体对象的元信息
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    default T fillMeta(T entity) {
        return EntityUtil.fillMeta(entity);
    }

    /**
     * 填充实体对象的元信息
     *
     * @param entity 实体对象
     * @param isNew  是否新对象
     * @return 实体对象
     */
    default T fillMeta(T entity, boolean isNew) {
        return EntityUtil.fillMeta(entity, isNew);
    }
}

