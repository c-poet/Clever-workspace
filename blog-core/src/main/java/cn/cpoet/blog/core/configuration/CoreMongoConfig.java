package cn.cpoet.blog.core.configuration;

import cn.cpoet.blog.core.mongo.CustomMappingMongoConverter;
import cn.cpoet.blog.core.mongo.MongoTemplate;
import cn.cpoet.blog.core.mongo.term.QueryGeneratorFactory;
import cn.cpoet.blog.core.support.EnumConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.Collections;

/**
 * MongoDb配置
 *
 * @author CPoet
 */
@Configuration
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories("cn.cpoet.blog.repo.repository")
public class CoreMongoConfig {

    @Primary
    @Bean({"reactiveMongoTemplate", "mongoTemplate"})
    public MongoTemplate monoTemplate(ReactiveMongoDatabaseFactory databaseFactory,
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
