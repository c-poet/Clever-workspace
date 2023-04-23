package cn.cpoet.workspace.core.configuration;

import cn.cpoet.workspace.core.mongo.CustomMappingMongoConverter;
import cn.cpoet.workspace.core.mongo.MongoTemplate;
import cn.cpoet.workspace.core.mongo.term.QueryGeneratorFactory;
import cn.cpoet.workspace.core.support.EnumConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collections;

/**
 * MongoDb配置
 *
 * @author CPoet
 */
@Configuration
@EnableMongoAuditing
@EnableMongoRepositories("cn.cpoet.blog.repo.repository")
public class CoreMongoConfig {

    @Primary
    @Bean("mongoTemplate")
    public MongoTemplate monoTemplate(MongoDatabaseFactory databaseFactory,
                                      MappingMongoConverter mongoConverter,
                                      QueryGeneratorFactory queryGeneratorFactory) {
        return new MongoTemplate(databaseFactory, mongoConverter, queryGeneratorFactory);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(EnumConverters.CUSTOM_ENUM_CONVERTERS);
    }

    @Bean("conversionService")
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        factoryBean.setConverters(Collections.singleton(EnumConverters.ENUM_CONVERTER_FACTORY));
        return factoryBean;
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoCustomConversions customConversions,
                                                       MongoMappingContext mappingContext) {
        CustomMappingMongoConverter converter = new CustomMappingMongoConverter(NoOpDbRefResolver.INSTANCE, mappingContext);
        converter.addConverterFactory(EnumConverters.ENUM_CONVERTER_FACTORY);
        converter.setCustomConversions(customConversions);
        EnumConverters.CUSTOM_ENUM_CONVERTERS.forEach(converter::addConverter);
        // 去除_class列
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }
}
