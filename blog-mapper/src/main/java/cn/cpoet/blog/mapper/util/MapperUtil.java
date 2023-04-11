package cn.cpoet.blog.mapper.util;

import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.api.context.AppContextHolder;
import cn.cpoet.blog.api.core.IdGenerator;
import cn.cpoet.blog.api.exception.AppException;
import cn.cpoet.blog.mapper.base.BaseMapper;
import cn.cpoet.blog.model.base.BaseEntity;
import cn.cpoet.blog.model.base.BaseRcEntity;
import org.apache.ibatis.binding.MapperProxy;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * mapper工具
 *
 * @author CPoet
 */
public abstract class MapperUtil {

    private final static Map<Class<?>, Class<?>> MAPPER_ENTITY_CLASS_CACHE = new ConcurrentHashMap<>();

    private static IdGenerator<Long> idGenerator;
    private static IdGenerator<String> idStrGenerator;

    private MapperUtil() {
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Class<T> getEntityByMapper(BaseMapper<T> baseMapper) {
        Class<? extends BaseMapper> mapperClass = baseMapper.getClass();
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
            idGenerator = AppContextHolder.getBean(SystemConst.ID_GENERATOR_SNOW_FLAKE, IdGenerator.class);
        }
        return idGenerator.nextId();
    }

    /**
     * 获取全局唯一性id
     *
     * @return id
     */
    @SuppressWarnings("unchecked")
    public static String nextIdStr() {
        if (idStrGenerator == null) {
            idStrGenerator = AppContextHolder.getBean(SystemConst.ID_GENERATOR_UUID, IdGenerator.class);
        }
        return idStrGenerator.nextId();
    }

    /**
     * 生成一个新的实体
     *
     * @param clazz 实体类型
     * @param <T>   实体类型
     * @return 实体
     */
    public static <T> T newPureEntity(Class<T> clazz) {
        try {
            return ReflectionUtils.accessibleConstructor(clazz).newInstance();
        } catch (Exception e) {
            throw new AppException("实例化对象失败", e);
        }
    }

    /**
     * 生成一个新的实体并填充信息
     *
     * @param clazz 实体类型
     * @param <T>   实体类型
     * @return 实体
     */
    public static <T> T newEntity(Class<T> clazz) {
        return fillMeta(() -> newPureEntity(clazz));
    }

    /**
     * 填充实体元信息
     *
     * @param entity 实体
     * @param <T>    实体类型
     * @return 实体
     */
    public static <T> T fillMeta(T entity) {
        if (entity instanceof BaseRcEntity) {
            if (!(entity instanceof BaseEntity)) {
                return fillMeta(entity, false);
            }
            BaseEntity baseEntity = (BaseEntity) entity;
            return fillMeta(entity, baseEntity.getVersion() == null || baseEntity.getId() == null);
        }
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
    public static <T> T fillMeta(T entity, boolean isNew) {
        if (entity instanceof BaseRcEntity) {
            Long userId = AppContextHolder.getAuthContext().curSubject().getId();
            LocalDateTime now = LocalDateTime.now();
            if (isNew) {
                BaseRcEntity baseRcEntity = (BaseRcEntity) entity;
                baseRcEntity.setId(nextId());
                baseRcEntity.setCreateUser(userId);
                baseRcEntity.setCreateTime(now);
                baseRcEntity.setDeleted(Boolean.FALSE);
            }
            if (entity instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) entity;
                baseEntity.setVersion(isNew ? 0L : System.currentTimeMillis());
                baseEntity.setUpdateUser(userId);
                baseEntity.setUpdateTime(now);
            }
        }
        return entity;
    }

    /**
     * 填充实体元信息
     *
     * @param supplier 实体生成方法
     * @param <T>      实体类型
     * @return 实体
     */
    public static <T> T fillMeta(Supplier<T> supplier) {
        return fillMeta(supplier.get());
    }

    /**
     * 填充实体元信息
     *
     * @param supplier 实体生成方法
     * @param isNew    是否新对象
     * @param <T>      实体类型
     * @return 实体
     */
    public static <T> T fillMeta(Supplier<T> supplier, boolean isNew) {
        return fillMeta(supplier.get(), isNew);
    }
}
