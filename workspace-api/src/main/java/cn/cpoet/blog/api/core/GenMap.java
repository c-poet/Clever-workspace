package cn.cpoet.blog.api.core;

import cn.cpoet.blog.api.context.AppContextHolder;
import cn.cpoet.blog.api.exception.AppException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;

/**
 * 通用传输对象
 *
 * @author CPoet
 */
public class GenMap implements Map<String, Object>, Serializable {

    private static final long serialVersionUID = 1L;

    private static ObjectMapper objMapper;

    private Map<String, Object> inner;

    public GenMap() {
    }

    public GenMap(Map<String, Object> inner) {
        this.inner = inner;
    }

    @JsonCreator
    public static GenMap of(Object obj) {
        if (obj instanceof GenMap) {
            return (GenMap) obj;
        }
        if (obj instanceof String) {
            return ofJson((String) obj);
        }
        Map<String, Object> map = getObjMapper().convertValue(obj, new TypeReference<Map<String, Object>>() {
        });
        return ofMap(map);
    }

    public static GenMap ofMap(Map<String, Object> map) {
        if (map instanceof GenMap) {
            return (GenMap) map;
        }
        return new GenMap(map);
    }

    public static GenMap ofJson(String str) {
        try {
            return getObjMapper().readValue(str, GenMap.class);
        } catch (Exception e) {
            throw new AppException("反序列化对象失败", e);
        }
    }

    public String toJson() {
        try {
            return getObjMapper().writeValueAsString(this);
        } catch (Exception e) {
            throw new AppException("序列化对象失败", e);
        }
    }

    public boolean has(String key) {
        return containsKey(key);
    }

    public boolean has(String key, Object value) {
        return has(key) && Objects.equals(get(key), value);
    }

    public <T> T get(String key, Class<T> clazz) {
        return convert2Obj(key, clazz, str -> getObjMapper().readValue(str, clazz), obj -> getObjMapper().convertValue(obj, clazz));
    }

    @SuppressWarnings("rawtypes")
    public <T> T get(String key, TypeReference<T> typeReference) {
        Type type = typeReference.getType();
        return convert2Obj(key, type instanceof Class ? (Class) type : null,
            str -> getObjMapper().readValue(str, typeReference), obj -> getObjMapper().convertValue(obj, typeReference));
    }

    public <T> T to(Class<T> clazz) {
        return getObjMapper().convertValue(this, clazz);
    }

    public <T> T to(TypeReference<T> typeReference) {
        return getObjMapper().convertValue(this, typeReference);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> T convert2Obj(String key, Class clazz, ConvertFunction<String, T> str2func, ConvertFunction<Object, T> objFunc) {
        if (clazz == String.class) {
            return (T) getString(key);
        } else if (clazz == Integer.class || clazz == int.class) {
            return (T) getInteger(key);
        } else if (clazz == Long.class || clazz == long.class) {
            return (T) getLong(key);
        } else if (clazz == Float.class || clazz == float.class) {
            return (T) getFloat(key);
        } else if (clazz == Double.class || clazz == double.class) {
            return (T) getDouble(key);
        } else if (clazz == Byte.class || clazz == byte.class) {
            return (T) getByte(key);
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            return (T) getBoolean(key);
        }
        Object obj = getObj(key);
        if (obj != null) {
            try {
                if (obj instanceof String) {
                    if (isJsonStr((String) obj)) {
                        return str2func.apply((String) obj);
                    }
                } else {
                    return objFunc.apply(obj);
                }
            } catch (Exception e) {
                throw new AppException("通用对象解析失败, key=" + key, e);
            }
        }
        return null;
    }

    public GenMap getMap(String key) {
        return get(key, GenMap.class);
    }

    public Object getObj(String key) {
        return get(key);
    }

    public Object getObj(String key, Object defaultVal) {
        Object obj = getObj(key);
        return obj == null ? defaultVal : obj;
    }

    public String getString(String key) {
        Object obj = getObj(key);
        return obj == null ? null : String.valueOf(obj);
    }

    public String getString(String key, String defaultVal) {
        String str = getString(key);
        return str == null ? defaultVal : str;
    }

    public Integer getInteger(String key) {
        String str = getString(key);
        return str == null ? null : Integer.parseInt(str);
    }

    public Integer getInteger(String key, Integer defaultVal) {
        Integer ig = getInteger(key);
        return ig == null ? defaultVal : ig;
    }

    public Long getLong(String key) {
        String str = getString(key);
        return str == null ? null : Long.parseLong(str);
    }

    public Long getLong(String key, Long defaultVal) {
        Long lg = getLong(key);
        return lg == null ? defaultVal : lg;
    }

    public Float getFloat(String key) {
        String str = getString(key);
        return str == null ? null : Float.parseFloat(str);
    }

    public Float getFloat(String key, Float defaultVal) {
        Float ft = getFloat(key);
        return ft == null ? defaultVal : ft;
    }

    public Double getDouble(String key) {
        String str = getString(key);
        return str == null ? null : Double.parseDouble(str);
    }

    public Double getDouble(String key, Double defaultVal) {
        Double db = getDouble(key);
        return db == null ? defaultVal : db;
    }

    public Byte getByte(String key) {
        String str = getString(key);
        return str == null ? null : Byte.parseByte(str);
    }

    public Byte getByte(String key, Byte defaultVal) {
        Byte bt = getByte(key);
        return bt == null ? defaultVal : bt;
    }

    public Boolean getBoolean(String key) {
        String str = getString(key);
        return str == null ? null : ("true".equalsIgnoreCase(str) || "yes".equalsIgnoreCase(str) || "on".equalsIgnoreCase(str)
            || "1".equalsIgnoreCase(str));
    }

    public Boolean getBoolean(String key, Boolean defaultVal) {
        Boolean bool = getBoolean(key);
        return bool == null ? defaultVal : bool;
    }

    private static ObjectMapper getObjMapper() {
        if (objMapper == null) {
            objMapper = AppContextHolder.getBean(ObjectMapper.class);
        }
        return objMapper;
    }

    /**
     * 判断是否是json串
     *
     * @param str
     * @return
     */
    private boolean isJsonStr(String str) {
        return (str.startsWith("{") && str.endsWith("}"))
            || (str.startsWith("[") && str.endsWith("]"));
    }

    @Override
    public int size() {
        return inner == null ? 0 : inner.size();
    }

    @Override
    public boolean isEmpty() {
        return inner == null || inner.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return inner != null && inner.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return inner != null && inner.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return inner == null ? null : inner.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return checkAndGetInner().put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return inner == null ? null : inner.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        checkAndGetInner().putAll(m);
    }

    @Override
    public void clear() {
        if (inner != null) {
            inner.clear();
        }
    }

    @Override
    public Set<String> keySet() {
        return inner == null ? Collections.emptySet() : inner.keySet();
    }

    @Override
    public Collection<Object> values() {
        return inner == null ? Collections.emptySet() : inner.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return inner == null ? Collections.emptySet() : inner.entrySet();
    }

    /**
     * 实例化{@link #inner}并获取
     *
     * @return
     */
    private Map<String, Object> checkAndGetInner() {
        if (inner == null) {
            inner = new HashMap<>(1 << 4);
        }
        return inner;
    }

    /**
     * 转换函数
     *
     * @param <T>
     * @param <R>
     */
    @FunctionalInterface
    private interface ConvertFunction<T, R> {
        /**
         * 应用转换函数
         *
         * @param t
         * @return
         * @throws Exception
         */
        R apply(T t) throws Exception;
    }
}
