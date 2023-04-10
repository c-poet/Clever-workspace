package cn.cpoet.blog.core.ibatis;

import cn.cpoet.blog.api.core.GenMap;
import cn.cpoet.blog.core.util.CamelUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.MapWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.util.Map;

/**
 * mybatis返回对象兼容处理
 * <p>下划线转驼峰</p>
 *
 * @author CPoet
 */
public class IBatisObjectWrapperFactory implements ObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        return object != null && GenMap.class.isAssignableFrom(object.getClass());
    }

    @Override
    @SuppressWarnings("unchecked")
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        return new GenMapWrapper(metaObject, (Map<String, Object>) object);
    }

    /**
     * map对象处理
     * <p>参照{@link org.apache.ibatis.reflection.wrapper.MapWrapper}</p>
     */
    private static class GenMapWrapper extends MapWrapper {

        public GenMapWrapper(MetaObject metaObject, Map<String, Object> map) {
            super(metaObject, map);
        }

        @Override
        public String findProperty(String name, boolean useCamelCaseMapping) {
            // 下划线转驼峰
            return useCamelCaseMapping ? CamelUtil.underline2Camel(name) : name;
        }
    }
}
