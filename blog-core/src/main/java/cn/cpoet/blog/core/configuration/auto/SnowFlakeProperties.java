package cn.cpoet.blog.core.configuration.auto;

import lombok.Data;

/**
 * 雪花算法相关配置
 *
 * @author CPoet
 */
@Data
public class SnowFlakeProperties {
    /**
     * 机器id
     */
    private Long workerId = 1L;

    /**
     * id起始序列（毫秒内）
     */
    private Long sequence = 0L;
}
