package cn.cpoet.workspace.ibatis.util;

import cn.cpoet.workspace.api.constant.SystemConst;
import cn.cpoet.workspace.api.context.AppContextHolder;
import cn.cpoet.workspace.api.core.IdGenerator;
import cn.cpoet.workspace.api.exception.AppException;
import cn.cpoet.workspace.ibatis.mapper.EntityMapper;
import org.apache.ibatis.binding.MapperProxy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体对象操作工具
 *
 * @author CPoet
 */
public abstract class EntityUtil {

    private static IdGenerator<Long> idGenerator;
    private final static Map<Class<?>, Class<?>> MAPPER_ENTITY_CLASS_CACHE = new ConcurrentHashMap<>();

    private EntityUtil() {
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Class<T> getClassByMapper(EntityMapper<T> baseMapper) {
        Class<? extends EntityMapper> mapperClass = baseMapper.getClass();
        Class<?> entityClass = MAPPER_ENTITY_CLASS_CACHE.get(mapperClass);
        if (entityClass == null) {
            try {
                MapperProxy mapperProxy = (MapperProxy) Proxy.getInvocationHandler(baseMapper);
                Field field = mapperProxy.getClass().getDeclaredField("mapperInterface");
                field.setAccessible(true);
                Class mapperInterfaceClass = (Class) field.get(mapperProxy);
                entityClass = (Class<T>) ((ParameterizedType) mapperInterfaceClass.getGenericInterfaces()[0]).getActualTypeArguments()[0];
                MAPPER_ENTITY_CLASS_CACHE.put(mapperInterfaceClass, entityClass);
            } catch (Exception e) {
                throw new AppException("获取反射对象目标对象类型失败", e);
            }
        }
        return (Class<T>) entityClass;
    }

    /**
     * 获取全局唯一性id
     *
     * @return id
     */
    @SuppressWarnings("unchecked")
    public static Long nextId() {
        if (idGenerator == null) {
            idGenerator = AppContextHolder.getAppContext().getBean(SystemConst.ID_GENERATOR, IdGenerator.class);
        }
        return idGenerator.nextId();
    }

    /**
     * 生成一个新的实体
     *
     * @param clazz 实体类型
     * @param <T>   实体类型
     * @return 实体
     */
    public static <T> T newEntity(Class<T> clazz) {
        return newEntity(clazz, true);
    }

    /**
     * 生成一个新的实体并填充信息
     *
     * @param clazz 实体类型
     * @param <T>   实体类型
     * @return 实体
     */
    public static <T> T newEntity(Class<T> clazz, boolean isFill) {
        try {
            T t = ReflectionUtils.accessibleConstructor(clazz).newInstance();
            return isFill ? fillEntity(t) : t;
        } catch (Exception e) {
            throw new AppException("实例化对象失败", e);
        }
    }

    /**
     * 填充实体元信息
     *
     * @param entity 实体
     * @param <T>    实体类型
     * @return 实体
     */
    public static <T> T fillEntity(T entity) {
//        if (entity instanceof BaseRcEntity) {
//            if (!(entity instanceof BaseEntity)) {
//                return fillEntity(entity, false);
//            }
//            BaseEntity baseEntity = (BaseEntity) entity;
//            return fillEntity(entity, baseEntity.getVersion() == null || baseEntity.getId() == null);
//        }
        return entity;
    }

    /**
     * 填充实体元信息
     *
     * @param entity 实体
     * @param isNew  是否新对象
     * @param <T>    实体类型
     * @return 实体
     */
    public static <T> T fillEntity(T entity, boolean isNew) {
//        if (entity instanceof BaseRcEntity) {
//            Long userId = AppContextHolder.getAuthContext().curSubject().getId();
//            LocalDateTime now = LocalDateTime.now();
//            if (isNew) {
//                BaseRcEntity baseRcEntity = (BaseRcEntity) entity;
//                baseRcEntity.setId(nextId());
//                baseRcEntity.setCreateUser(userId);
//                baseRcEntity.setCreateTime(now);
//                baseRcEntity.setDeleted(Boolean.FALSE);
//            }
//            if (entity instanceof BaseEntity) {
//                BaseEntity baseEntity = (BaseEntity) entity;
//                if (isNew) {
//                    baseEntity.setVersion(0);
//                }
//                baseEntity.setUpdateUser(userId);
//                baseEntity.setUpdateTime(now);
//            }
//        }
        return entity;
    }
}
