package cn.cpoet.blog.mapper.base;


import cn.cpoet.blog.mapper.util.MapperUtil;

/**
 * @author CPoet
 */
public interface BaseMapper<T> {
    /**
     * 获取实体类型
     *
     * @return 实体类型
     */
    default Class<T> getEntityClass() {
        return MapperUtil.getEntityByMapper(this);
    }

    /**
     * 实例化一个实体对象
     *
     * @return 实体对象
     */
    default T newPureEntity() {
        return MapperUtil.newPureEntity(getEntityClass());
    }

    /**
     * 实例化一个实体对象
     *
     * @return 实例化实体对象
     */
    default T newEntity() {
        return MapperUtil.newEntity(getEntityClass());
    }

    /**
     * 填充实体对象的元信息
     *
     * @param entity 实体对象
     * @return 实体对象
     */
    default T fillMeta(T entity) {
        return MapperUtil.fillMeta(entity);
    }

    /**
     * 填充实体对象的元信息
     *
     * @param entity 实体对象
     * @param isNew  是否新对象
     * @return 实体对象
     */
    default T fillMeta(T entity, boolean isNew) {
        return MapperUtil.fillMeta(entity, isNew);
    }
}
