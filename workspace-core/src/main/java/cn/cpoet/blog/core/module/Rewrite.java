package cn.cpoet.blog.core.module;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * @author CPoet
 */
@Data
@Setter(AccessLevel.PACKAGE)
public class Rewrite {
    /**
     * 是否继承上级
     */
    private boolean parent;

    /**
     * 是否开启
     */
    private boolean enabled;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 分隔符
     */
    private String separator;
}
