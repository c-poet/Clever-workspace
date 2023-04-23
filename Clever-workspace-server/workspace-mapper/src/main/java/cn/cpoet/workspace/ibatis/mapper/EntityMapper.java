package cn.cpoet.workspace.ibatis.mapper;

import cn.cpoet.workspace.ibatis.util.EntityUtil;
import tk.mybatis.mapper.annotation.RegisterMapper;

/**
 * 实体操作方法定义
 *
 * @author CPoet
 */
@RegisterMapper
public interface EntityMapper<T> {
    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    default Class<T> getEntityClass() {
        return EntityUtil.getClassByMapper(this);
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
     * 实例化一个实体对象
     *
     * @param isFill  是否填充信息
     * @return 实体对象
     */
    default T newEntity(boolean isFill) {
        return EntityUtil.newEntity(getEntityClass(), isFill);
    }

    /**
     * 填充实体对象的元信息
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    default T fillEntity(T entity) {
        return EntityUtil.fillEntity(entity);
    }

    /**
     * 填充实体对象的元信息
     *
     * @param entity 实体对象
     * @param isNew  是否新对象
     * @return 实体对象
     */
    default T fillEntity(T entity, boolean isNew) {
        return EntityUtil.fillEntity(entity, isNew);
    }
}
