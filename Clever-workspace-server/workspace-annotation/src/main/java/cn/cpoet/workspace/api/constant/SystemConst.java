package cn.cpoet.workspace.api.constant;

/**
 * 系统公共常量
 *
 * @author CPoet
 */
public interface SystemConst {
    /**
     * 默认id
     */
    long DEFAULT_ID = -1;

    /**
     * 默认父级id
     */
    long DEFAULT_PID = DEFAULT_ID;

    /**
     * 系统用户id
     */
    long SYSTEM_USER_ID = DEFAULT_ID;

    /**
     * 配置前缀
     */
    String CONF_PREFIX = "app.blog";

    /**
     * 默认id生成器
     */
    String ID_GENERATOR = "idGenerator";

    /**
     * id生成器，雪花算法
     */
    String ID_GENERATOR_SNOW_FLAKE = "snowFlakeIdGenerator";

    /**
     * id生成器，uuid
     */
    String ID_GENERATOR_UUID = "uuidIdGenerator";
}
