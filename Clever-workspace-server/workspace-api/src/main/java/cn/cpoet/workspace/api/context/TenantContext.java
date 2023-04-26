package cn.cpoet.workspace.api.context;

/**
 * @author CPoet
 */
public interface TenantContext {
    /**
     * 获取当前上下文中的租户
     *
     * @return 租户信息
     */
    Tenant curTenant();
}
