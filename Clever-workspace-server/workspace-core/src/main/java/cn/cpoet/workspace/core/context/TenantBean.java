package cn.cpoet.workspace.core.context;

import cn.cpoet.workspace.api.constant.SystemConst;
import cn.cpoet.workspace.api.context.Tenant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author CPoet
 */
@Getter
@RequiredArgsConstructor
public class TenantBean implements Tenant {
    /**
     * 系统租户
     */
    public final static Tenant SYS_TENANT = new TenantBean(SystemConst.SYSTEM_TENANT_ID, SystemConst.SYSTEM_TENANT_NAME);

    private final Long id;
    private final String name;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
