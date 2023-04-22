package cn.cpoet.blog.core.mongo.term;

import cn.cpoet.blog.api.annotation.term.*;
import cn.cpoet.blog.core.util.BeanUtil;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CPoet
 */
@Component
public class GeneralTermGenAdapter implements TermGenAdapter {

    private final static Map<Class<?>, String> TOKEN_MAPPING = new HashMap<>();

    static {
        TOKEN_MAPPING.put(Eq.class, "is");
        TOKEN_MAPPING.put(Ge.class, "gte");
        TOKEN_MAPPING.put(Gt.class, "gt");
        TOKEN_MAPPING.put(In.class, "in");
        TOKEN_MAPPING.put(Le.class, "lte");
        TOKEN_MAPPING.put(Like.class, "regex");
        TOKEN_MAPPING.put(Lt.class, "lt");
        TOKEN_MAPPING.put(Ne.class, "ne");
        TOKEN_MAPPING.put(Nin.class, "nin");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Annotation>[] accepts() {
        return TOKEN_MAPPING.keySet().toArray(new Class[0]);
    }

    /**
     * 拼接生成校验语句
     *
     * @param field      属性
     * @param methodName 读取方法名称
     * @param statement  语句
     * @return 带校验的语句
     */
    protected String genCheckStatement(Field field, String methodName, String statement) {
        if (CharSequence.class.isAssignableFrom(field.getType())) {
            return "if(org.springframework.util.StringUtils.hasText(b." + methodName + ")){" + statement + "}";
        }
        if (Collection.class.isAssignableFrom(field.getType())) {
            return "if(!org.springframework.util.CollectionUtils.isEmpty(b." + methodName + ")){" + statement + "}";
        }
        if (Object[].class.isAssignableFrom(field.getType())) {
            return "if(b." + methodName + " != null && " + "b." + methodName + ".length() > 0){" + statement + "}";
        }
        return "if(b." + methodName + " != null) {" + statement + "}";
    }

    private String getToken(Annotation annotation) {
        Class<? extends Annotation> clazz = annotation.annotationType();
        return TOKEN_MAPPING.get(clazz);
    }

    @Override
    public String genStatement(Class<?> beanClass, Field field, Annotation annotation) {
        Map<String, Object> attrs = AnnotationUtils.getAnnotationAttributes(annotation);
        String readMethod = BeanUtil.getIntactReadMethodName(beanClass, field);
        String value = String.valueOf(attrs.get("value"));
        if (!StringUtils.hasText(value)) {
            value = field.getName();
        }
        String fieldName = value;
        String token = getToken(annotation);
        if (annotation instanceof Like) {
            Like like = (Like) annotation;
            if (!like.left() && !like.right()) {
                return null;
            }
            String statement = "c = c.and(\"" + fieldName + "\")." + token + "(";
            if (like.left()) {
                statement += "\".*\" + ";
            }
            statement += "b." + readMethod;
            if (like.right()) {
                statement += " + \".*\"";
            }
            return genCheckStatement(field, readMethod, statement + ");");
        }
        return genCheckStatement(field, readMethod, "c = c.and(\"" + fieldName + "\")." + token + "(b." + readMethod + ");");
    }
}