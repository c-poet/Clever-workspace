package cn.cpoet.blog.core.support;

import cn.cpoet.blog.api.core.Dict;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 枚举字典处理
 *
 * @author CPoet
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EnumDictHandler implements InitializingBean {

    /**
     * 扫描的包前缀，提升启动速度
     */
    private final static String PACKAGE_PREFIX = "/cn/cpoet";
    private final static String CLASS_PATTERN = "/**/*.class";
    private final ResourcePatternResolver resourcePatternResolver;
    private final Map<String, List<Dict>> enumDictMapping = new HashMap<>();

    /**
     * 获取所有的枚举字典
     *
     * @return 获取所有枚举字典
     */
    public Map<String, List<Dict>> getAllDict() {
        return Collections.unmodifiableMap(enumDictMapping);
    }

    /**
     * 获取字典
     *
     * @param dictCode 字典编码
     * @return 字典值
     */
    public List<Dict> getDict(String dictCode) {
        return enumDictMapping.get(dictCode);
    }

    private void doScanEnumDict() {
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + PACKAGE_PREFIX + CLASS_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
            for (Resource resource : resources) {
                ClassMetadata classMetadata = readerFactory.getMetadataReader(resource).getClassMetadata();
                if (isDictImpl(classMetadata)) {
                    Class<?> clazz = Class.forName(classMetadata.getClassName());
                    if (clazz.isEnum()) {
                        genEnumDict(clazz);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("扫描枚举字典失败：{}", e.getMessage(), e);
        }
    }

    private boolean isDictImpl(ClassMetadata classMetadata) {
        String[] interfaceNames = classMetadata.getInterfaceNames();
        if (interfaceNames.length > 0) {
            for (String interfaceName : interfaceNames) {
                if (interfaceName.equals(Dict.class.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void genEnumDict(Class<?> clazz) {
        Object[] constants = clazz.getEnumConstants();
        if (constants.length > 0) {
            List<Dict> dictList = new ArrayList<>();
            for (Object constant : constants) {
                DictImpl dict = new DictImpl((Dict) constant);
                dictList.add(dict);
            }
            enumDictMapping.put(clazz.getSimpleName(), dictList);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.doScanEnumDict();
    }

    @Data
    private static class DictImpl implements Dict {
        private final String code;
        private final String label;
        private final Object value;
        private final String desc;

        public DictImpl(Dict dict) {
            this.code = dict.getCode();
            this.label = dict.getLabel();
            this.value = dict.getValue();
            this.desc = dict.getDesc();
        }
    }
}
