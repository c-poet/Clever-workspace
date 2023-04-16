package cn.cpoet.blog.repo.configuration;

import cn.cpoet.blog.api.core.ConverterFactoryGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author CPoet
 */
@Configuration
@EnableReactiveMongoAuditing
@EnableReactiveMongoRepositories("cn.cpoet.blog.repo.repository")
@RequiredArgsConstructor
public class MongoConfig {

    private final ConverterFactoryGenerator converterFactoryGenerator;

//    @Bean
//    @Primary
//    public MongoCustomConversions mongoCustomConversions() {
//        return new MongoCustomConversions(EnumConverters.CUSTOM_ENUM_CONVERTERS);
//    }
}
