package cn.cpoet.workspace.api.core;

/**
 * 分页接口
 *
 * @author CPoet
 */
public interface Pageable {
    /**
     * 获取页号
     *
     * @return 页号
     */
    int getPageNo();

    /**
     * 获取页大小
     *
     * @return 页大小
     */
    int getPageSize();
}
