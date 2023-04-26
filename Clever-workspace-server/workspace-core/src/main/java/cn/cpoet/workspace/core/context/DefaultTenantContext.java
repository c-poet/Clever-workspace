package cn.cpoet.workspace.core.context;

import cn.cpoet.workspace.api.context.Tenant;
import cn.cpoet.workspace.api.context.TenantContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author CPoet
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultTenantContext implements TenantContext {

    @Override
    public Tenant curTenant() {
        return TenantBean.SYS_TENANT;
    }
}
