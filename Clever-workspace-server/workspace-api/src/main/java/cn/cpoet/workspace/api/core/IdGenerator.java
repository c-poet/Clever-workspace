package cn.cpoet.workspace.api.core;

/**
 * Id生成器
 *
 * @author CPoet
 */
public interface IdGenerator<T> {
    /**
     * 获取生成器名称
     *
     * @return 生成器名称
     */
    String getName();

    /**
     * 获取下一个有效id
     *
     * @return 有效id
     */
    T nextId();
}
