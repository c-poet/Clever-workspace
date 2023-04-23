package cn.cpoet.workspace.api.core;

/**
 * 响应状态
 *
 * @author CPoet
 */
public interface Status {
    /**
     * 响应码
     *
     * @return 响应码
     */
    String code();

    /**
     * 响应消息
     *
     * @return 响应消息
     */
    String message();
}
