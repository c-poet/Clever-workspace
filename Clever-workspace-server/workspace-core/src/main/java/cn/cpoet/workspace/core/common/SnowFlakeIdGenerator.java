package cn.cpoet.workspace.core.common;

import cn.cpoet.workspace.api.constant.SystemConst;
import cn.cpoet.workspace.api.core.IdGenerator;
import lombok.extern.slf4j.Slf4j;

/**
 * 基于雪花算法的Id生成器
 *
 * <description>
 * 1. 首位保持0，确保正数
 * 2. 45 bit 时间戳
 * 3. 6 bit 机器id, 2^5 - 1 = 63
 * 4. 12 bit 同毫秒内的序列数，2^12 - 1 = 4095
 * <p>
 * 可用时长：((2^45 - 1) - {@link #STARTING_TIME}) / (356 * 24 * 60 * 60 * 1000)
 * </description>
 *
 * @author cpoet
 */
@Slf4j
public class SnowFlakeIdGenerator implements IdGenerator<Long> {
    /**
     * 时间初始值
     * 可用时长：1064 year
     */
    private final static long STARTING_TIME = 1620283889907L;

    /**
     * 机器id位数
     */
    private final static long WORKER_ID_BIT = 6L;

    /**
     * 每毫秒内产生的id数
     */
    private final static long SEQUENCE_BIT = 12L;

    /**
     * 最大机器id的数量
     */
    private final static long MAX_WORKER_ID = ~(-1L << WORKER_ID_BIT);

    /**
     * 每毫秒产生的序列最大数
     */
    private final static long SEQUENCE_MASK = ~(-1L << SEQUENCE_BIT);

    /**
     * 机器id需要左移位数
     */
    private final static long WORKER_ID_SHIFT = SEQUENCE_BIT;

    /**
     * 时间戳需要左移位数
     */
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BIT + WORKER_ID_BIT;

    /**
     * 默认的毫秒内序列的起始值
     */
    private final static long DEFAULT_INIT_SEQUENCE = 0;

    /**
     * 记录产生时间毫秒数，判断是否是同一毫秒
     */
    private long lastTimestamp = -1L;

    /**
     * 机器ID
     */
    private final long workerId;

    /**
     * 一毫秒内生成的多个id的最新序号
     */
    private long sequence;

    /**
     * 构造器
     *
     * @param workerId 机器id
     * @param sequence 毫秒内序列的起始值
     */
    public SnowFlakeIdGenerator(long workerId, long sequence) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("机器id支持的数量在 0 - %d 间.", MAX_WORKER_ID));
        }
        this.workerId = workerId;
        this.sequence = sequence;
    }

    /**
     * 构造器
     *
     * @param workerId 机器id
     */
    public SnowFlakeIdGenerator(long workerId) {
        this(workerId, DEFAULT_INIT_SEQUENCE);
    }

    @Override
    public String getName() {
        return SystemConst.ID_GENERATOR_SNOW_FLAKE;
    }

    @Override
    public synchronized Long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            log.error("机器时间错误，时钟向后偏移 {}.", lastTimestamp);
            throw new RuntimeException(String.format("机器时间错误，时钟向后偏移 %d ms", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return ((timestamp - STARTING_TIME) << TIMESTAMP_LEFT_SHIFT)
            | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    public long getWorkerId() {
        return workerId;
    }

    /**
     * 当某一毫秒的时间，产生的id数。超过设定的最大序列数，系统会进入等待，直到下一毫秒，系统继续产生ID。
     *
     * @param lastTimestamp lastTimestamp
     * @return timestamp
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 获取当前时间戳
     *
     * @return timestamp
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }
}
