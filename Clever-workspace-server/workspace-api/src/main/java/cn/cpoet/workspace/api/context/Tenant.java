package cn.cpoet.workspace.api.context;

/**
 * @author CPoet
 */
public interface Tenant {
    /**
     * 获取当前租户id
     *
     * @return 当前租户id
     */
    Long getId();

    /**
     * 获取当前租户名称
     *
     * @return 租户名称
     */
    String getName();
}
