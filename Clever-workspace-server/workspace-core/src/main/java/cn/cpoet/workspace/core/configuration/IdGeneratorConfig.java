package cn.cpoet.workspace.core.configuration;

import cn.cpoet.workspace.api.constant.SystemConst;
import cn.cpoet.workspace.core.common.SnowFlakeIdGenerator;
import cn.cpoet.workspace.core.common.UUIDGenerator;
import cn.cpoet.workspace.core.configuration.auto.SnowFlakeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CPoet
 */
@Configuration
public class IdGeneratorConfig {

    @Bean
    @ConfigurationProperties(prefix = SystemConst.CONF_PREFIX + ".id-generator.snow-flake")
    public SnowFlakeProperties snowFlakeProperties() {
        return new SnowFlakeProperties();
    }

    @Bean({SystemConst.ID_GENERATOR, SystemConst.ID_GENERATOR_SNOW_FLAKE})
    public SnowFlakeIdGenerator snowFlakeIdGenerator(SnowFlakeProperties snowFlakeProperties) {
        return new SnowFlakeIdGenerator(snowFlakeProperties.getWorkerId(), snowFlakeProperties.getSequence());
    }

    public UUIDGenerator uuidGenerator() {
        return new UUIDGenerator();
    }
}
