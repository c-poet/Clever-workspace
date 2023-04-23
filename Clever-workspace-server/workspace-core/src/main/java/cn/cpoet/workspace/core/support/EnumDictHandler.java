package cn.cpoet.workspace.core.support;

import cn.cpoet.workspace.api.annotation.EnumDict;
import cn.cpoet.workspace.api.core.GenMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    private final EnumHandler enumHandler;
    private final ResourcePatternResolver resourcePatternResolver;
    private final Map<String, Class<? extends Enum<?>>> enumDictMapping = new HashMap<>();

    /**
     * 获取字典
     *
     * @param dictCode 字典编码
     * @return 字典值
     */
    public List<GenMap> getDict(String dictCode) {
        Class<? extends Enum<?>> clazz = enumDictMapping.get(dictCode);
        if (clazz == null) {
            return Collections.emptyList();
        }
        Enum<?>[] constants = clazz.getEnumConstants();
        if (constants.length == 0) {
            return Collections.emptyList();
        }
        List<GenMap> dictList = new ArrayList<>(constants.length);
        for (Enum<?> constant : constants) {
            dictList.add(enumHandler.getEnumAppear(constant));
        }
        return dictList;
    }

    private void doScanEnumDict() {
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + PACKAGE_PREFIX + CLASS_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
            for (Resource resource : resources) {
                AnnotationMetadata annotationMetadata = readerFactory.getMetadataReader(resource).getAnnotationMetadata();
                if (annotationMetadata.hasAnnotation(EnumDict.class.getName())) {
                    Class<?> clazz = Class.forName(annotationMetadata.getClassName());
                    if (clazz.isEnum()) {
                        genEnumDict(clazz);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("扫描枚举字典失败：{}", e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private void genEnumDict(Class<?> clazz) {
        EnumDict enumDict = clazz.getAnnotation(EnumDict.class);
        String dictCode = StringUtils.hasText(enumDict.value()) ? enumDict.value() : clazz.getSimpleName();
        enumDictMapping.put(dictCode, (Class<? extends Enum<?>>) clazz);
    }

    @Override
    public void afterPropertiesSet() {
        this.doScanEnumDict();
    }
}
