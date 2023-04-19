package cn.cpoet.blog.api.core;

/**
 * 字典定义接口
 *
 * @author CPoet
 */
public interface Dict {
    /**
     * 字典编码
     *
     * @return 字典编码
     */
    default String getCode() {
        return String.valueOf(getValue());
    }

    /**
     * 字典名称
     *
     * @return 字典名称
     */
    String getLabel();

    /**
     * 字典值
     *
     * @return 字典值
     */
    Object getValue();

    /**
     * 字典描述
     *
     * @return 字典描述
     */
    default String getDesc() {
        return getLabel();
    }
}
