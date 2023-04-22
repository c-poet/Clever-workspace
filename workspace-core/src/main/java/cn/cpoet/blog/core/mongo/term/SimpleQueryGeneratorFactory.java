package cn.cpoet.blog.core.mongo.term;

import cn.cpoet.blog.api.annotation.term.And;
import cn.cpoet.blog.api.annotation.term.Or;
import cn.cpoet.blog.api.annotation.term.Order;
import cn.cpoet.blog.api.constant.Logic;
import cn.cpoet.blog.api.constant.OrderMode;
import cn.cpoet.blog.core.param.PageParam;
import cn.cpoet.blog.core.util.BeanUtil;
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
 * @author CPoet
 */
@Component
@SuppressWarnings("unchecked")
public class SimpleQueryGeneratorFactory implements QueryGeneratorFactory {

    public final static String BEAN_OBJ_NAME = "b";
    public final static String QUERY_OBJ_NAME = "q";
    public final static String CRITERIA_OBJ_NAME = "c";
    private final static String CRITERIA_OBJ_TEMP_NAME = "t";
    private final Map<Class<? extends Annotation>, TermGenAdapter> termGenAdapterMapping;
    private final Map<Class<?>, QueryGenerator<?>> generatorMapping = new ConcurrentHashMap<>();

    public SimpleQueryGeneratorFactory(List<TermGenAdapter> termGenAdapters) {
        termGenAdapterMapping = new HashMap<>(termGenAdapters.size());
        for (TermGenAdapter termGenAdapter : termGenAdapters) {
            Class<? extends Annotation>[] accepts = termGenAdapter.accepts();
            for (Class<? extends Annotation> accept : accepts) {
                termGenAdapterMapping.put(accept, termGenAdapter);
            }
        }
    }

    @Override
    public Query getQuery(Object bean) {
        return get(bean).generate(bean);
    }

    @Override
    public <T> QueryGenerator<T> get(T bean) {
        return (QueryGenerator<T>) get(bean.getClass());
    }

    @Override
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
            String body = "{" + beanClass.getName() + " " + BEAN_OBJ_NAME + " = (" + beanClass.getName() + ")" + "$1;"
                + genMethodBody(beanClass) + "}";
            ctMethod.setBody(body);
            ctClass.addMethod(ctMethod);
            return (QueryGenerator<T>) ctClass.toClass().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("实例化查询生成类失败", e);
        }
    }

    private String genMethodBody(Class<?> clazz) {
        StringBuilder sb = new StringBuilder()
            .append("org.springframework.data.mongodb.core.query.Criteria ").append(CRITERIA_OBJ_NAME)
            .append("=new org.springframework.data.mongodb.core.query.Criteria();")
            .append("org.springframework.data.mongodb.core.query.Criteria ").append(CRITERIA_OBJ_TEMP_NAME).append(" = null;");
        Logic globalLogic = getLogic(clazz, Logic.AND);
        List<Field> fields = BeanUtil.getAllFields(clazz);
        Map<Field, Order> orderMapping = null;
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
                    if (orderMapping == null) {
                        orderMapping = new LinkedHashMap<>();
                    }
                    orderMapping.put(field, order);
                }
            }
        }
        sb.append("org.springframework.data.mongodb.core.query.Query ").append(QUERY_OBJ_NAME)
            .append("=org.springframework.data.mongodb.core.query.Query.query(").append(CRITERIA_OBJ_NAME).append(");");
        handleOrder(sb, clazz, orderMapping);
        handlePageable(sb, clazz);
        return sb.append("return ").append(QUERY_OBJ_NAME).append(";").toString();
    }

    private void handleOrder(StringBuilder sb, Class<?> clazz, Map<Field, Order> orderMap) {
        if (!CollectionUtils.isEmpty(orderMap)) {
            sb.append("java.util.List orders = new java.util.ArrayList();");
            for (Map.Entry<Field, Order> entry : orderMap.entrySet()) {
                Field field = entry.getKey();
                Order order = entry.getValue();
                Class<?> fieldType = field.getType();
                String readMethod = BeanUtil.getIntactReadMethodName(clazz, field);
                if (Collection.class.isAssignableFrom(fieldType)) {
                    sb.append("if(!org.springframework.util.CollectionUtils.isEmpty(b.")
                        .append(readMethod).append(")){")
                        .append("java.util.Iterator it=b.").append(readMethod).append(".iterator();")
                        .append("while(it.hasNext()){String next=(String)it.next();")
                        .append("orders.add(").append(getStatementByModel("next", order.value()))
                        .append(");}}");
                } else if (Map.class.isAssignableFrom(fieldType)) {
                    sb.append("if(!org.springframework.util.CollectionUtils.isEmpty(b.")
                        .append(readMethod).append(")){")
                        .append("java.util.Iterator it=b.").append(readMethod).append(".entrySet().iterator();")
                        .append("while(it.hasNext()){java.util.Map.Entry next=it.next();")
                        .append("if(cn.cpoet.blog.api.constant.OrderMode.DESC.equals(next.getValue())){")
                        .append("orders.add(org.springframework.data.domain.Sort.Order.desc((String)next.getKey()));}else{")
                        .append("orders.add(org.springframework.data.domain.Sort.Order.asc((String)next.getKey()));}}}");
                } else if (String.class.isAssignableFrom(fieldType)) {
                    sb.append("if (org.springframework.util.StringUtils.hasText(b.")
                        .append(readMethod).append(")){")
                        .append("orders.add(").append(getStatementByModel("b." + readMethod, order.value()))
                        .append(");}");
                } else {
                    throw new IllegalStateException("不受支持的排序对象类型:" + fieldType.getName());
                }
                if (order.fields().length > 0) {
                    sb.append("else{");
                    for (String ofl : order.fields()) {
                        sb.append("orders.add(").append(getStatementByModel("\"" + ofl + "\"", order.value())).append(");");
                    }
                    sb.append("}");
                }
            }
            sb.append("if (!org.springframework.util.CollectionUtils.isEmpty(orders)){")
                .append(QUERY_OBJ_NAME).append("=").append(QUERY_OBJ_NAME).append(".with(")
                .append("org.springframework.data.domain.Sort.by(").append("orders));}");
        }
    }

    private String getStatementByModel(String name, OrderMode orderMode) {
        if (OrderMode.DESC.equals(orderMode)) {
            return "org.springframework.data.domain.Sort.Order.desc(" + name + ")";
        }
        return "org.springframework.data.domain.Sort.Order.asc(" + name + ")";
    }

    private void handlePageable(StringBuilder sb, Class<?> clazz) {
        if (PageParam.class.isAssignableFrom(clazz)) {
            sb.append(QUERY_OBJ_NAME).append("=").append(QUERY_OBJ_NAME)
                .append(".with(").append(BEAN_OBJ_NAME).append(".toPageable());");
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
