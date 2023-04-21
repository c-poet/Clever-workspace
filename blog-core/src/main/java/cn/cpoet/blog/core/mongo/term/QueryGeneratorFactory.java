package cn.cpoet.blog.core.mongo.term;

import cn.cpoet.blog.api.annotation.term.And;
import cn.cpoet.blog.api.annotation.term.Or;
import cn.cpoet.blog.api.annotation.term.Order;
import cn.cpoet.blog.api.constant.Logic;
import cn.cpoet.blog.api.constant.OrderMode;
import cn.cpoet.blog.core.param.PageParam;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 查询生成器工厂
 *
 * @author CPoet
 */
@Component
@SuppressWarnings("unchecked")
public class QueryGeneratorFactory {

    public final static String BEAN_OBJ_NAME = "b";
    public final static String QUERY_OBJ_NAME = "q";
    public final static String CRITERIA_OBJ_NAME = "c";
    private final static String CRITERIA_OBJ_TEMP_NAME = "t";
    private final Map<Class<? extends Annotation>, TermGenAdapter> termGenAdapterMapping;
    private final Map<Class<?>, QueryGenerator<?>> generatorMapping = new ConcurrentHashMap<>();

    public QueryGeneratorFactory(List<TermGenAdapter> termGenAdapters) {
        termGenAdapterMapping = new HashMap<>(termGenAdapters.size());
        for (TermGenAdapter termGenAdapter : termGenAdapters) {
            Class<? extends Annotation>[] accepts = termGenAdapter.accepts();
            for (Class<? extends Annotation> accept : accepts) {
                termGenAdapterMapping.put(accept, termGenAdapter);
            }
        }
    }

    public <T> QueryGenerator<T> get(Class<T> beanClass) {
        QueryGenerator<?> queryGenerator = generatorMapping.get(beanClass);
        if (queryGenerator != null) {
            return (QueryGenerator<T>) queryGenerator;
        }
        QueryGenerator<T> generator = createGenerator(beanClass);
        generatorMapping.put(beanClass, generator);
        return generator;
    }

    private <T> QueryGenerator<T> createGenerator(Class<T> beanClass) {
        try {
            ClassPool classPool = ClassPool.getDefault();
            String beanName = beanClass.getSimpleName();
            CtClass ctClass = classPool.makeClass(beanName + "QueryGenerator");
            CtClass queryGeneratorClass = classPool.get(QueryGenerator.class.getName());
            ctClass.setInterfaces(new CtClass[]{queryGeneratorClass});
            CtMethod ctMethod = new CtMethod(classPool.get(Query.class.getName()), "generate", new CtClass[]{}, ctClass);
            ctMethod.addParameter(classPool.get(Object.class.getName()));
            String sb = "{" + beanClass.getName() + " " + BEAN_OBJ_NAME + " = (" + beanClass.getName() + ")" + "$1;"
                + genMethodBody(beanClass) + "}";
            ctMethod.setBody(sb);
            ctClass.addMethod(ctMethod);
            ctClass.writeFile("E:\\OpenSource\\Clever-blog\\blog-core\\src\\main\\java\\cn\\cpoet\\blog\\core\\mongo\\term");
            return (QueryGenerator<T>) ctClass.toClass().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("实例化查询生成类失败", e);
        }
    }

    private String genMethodBody(Class<?> clazz) {
        StringBuilder sb = new StringBuilder()
            .append("org.springframework.data.mongodb.core.query.Criteria ").append(CRITERIA_OBJ_NAME).append("=new org.springframework.data.mongodb.core.query.Criteria();")
            .append("org.springframework.data.mongodb.core.query.Criteria ").append(CRITERIA_OBJ_TEMP_NAME).append(" = null;");
        Logic globalLogic = getLogic(clazz, Logic.AND);
        Field[] fields = clazz.getDeclaredFields();
        Map<String, OrderMode> orderMap = null;
        for (Field field : fields) {
            for (Map.Entry<Class<? extends Annotation>, TermGenAdapter> entry : termGenAdapterMapping.entrySet()) {
                Annotation annotation = field.getAnnotation(entry.getKey());
                if (annotation != null) {
                    Logic logic = getLogic(field, globalLogic);
                    if (Logic.OR.equals(logic)) {
                        sb.append(CRITERIA_OBJ_TEMP_NAME).append("=").append(CRITERIA_OBJ_NAME).append(";")
                            .append(CRITERIA_OBJ_NAME).append("=new org.springframework.data.mongodb.core.query.Criteria();");
                    }
                    String statement = entry.getValue().genStatement(clazz, field, annotation);
                    if (StringUtils.hasText(statement)) {
                        sb.append(statement);
                    }
                    if (Logic.OR.equals(logic)) {
                        if (StringUtils.hasText(statement)) {
                            sb.append(CRITERIA_OBJ_TEMP_NAME).append(".orOperator(").append(CRITERIA_OBJ_NAME).append(");");
                        }
                        sb.append(CRITERIA_OBJ_NAME).append("=").append(CRITERIA_OBJ_TEMP_NAME).append(";");
                    }
                    break;
                }
                Order order = field.getAnnotation(Order.class);
                if (order != null) {
                    if (orderMap == null) {
                        orderMap = new LinkedHashMap<>();
                    }
                    orderMap.put(StringUtils.hasText(order.value()) ? order.value() : field.getName(), order.mode());
                }
            }
        }
        sb.append("org.springframework.data.mongodb.core.query.Query ").append(QUERY_OBJ_NAME)
            .append("=org.springframework.data.mongodb.core.query.Query.query(").append(CRITERIA_OBJ_NAME).append(");");
        handleOrder(sb, orderMap);
        handlePageable(sb, clazz);
        return sb.append("return ").append(QUERY_OBJ_NAME).append(";").toString();
    }

    private void handleOrder(StringBuilder sb, Map<String, OrderMode> orderMap) {
        if (!CollectionUtils.isEmpty(orderMap)) {
            StringJoiner sj = new StringJoiner(",", "org.springframework.data.domain.Sort.by(", ")");
            for (Map.Entry<String, OrderMode> entry : orderMap.entrySet()) {
                if (OrderMode.DESC.equals(entry.getValue())) {
                    sj.add("org.springframework.data.domain.Sort.Order.asc(" + entry.getKey() + ")");
                } else {
                    sj.add("org.springframework.data.domain.Sort.Order.desc(" + entry.getKey() + ")");
                }
            }
            sb.append(QUERY_OBJ_NAME).append("=").append(QUERY_OBJ_NAME).append(".with(").append(sj).append(");");
        }
    }

    private void handlePageable(StringBuilder sb, Class<?> clazz) {
        if (PageParam.class.isAssignableFrom(clazz)) {
            sb.append(QUERY_OBJ_NAME).append("=").append(QUERY_OBJ_NAME)
                .append(".with(").append(BEAN_OBJ_NAME).append(".toPageRequest());");
        }
    }

    private Logic getLogic(AnnotatedElement element, Logic defaultLogic) {
        And and = element.getAnnotation(And.class);
        if (and != null) {
            return Logic.AND;
        }
        Or or = element.getAnnotation(Or.class);
        return or == null ? defaultLogic : Logic.OR;
    }
}
