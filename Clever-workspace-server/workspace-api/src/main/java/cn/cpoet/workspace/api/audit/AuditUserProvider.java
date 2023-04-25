package cn.cpoet.workspace.api.audit;

/**
 * 审计人员信息提供
 *
 * @author CPoet
 */
public interface AuditUserProvider<T> {
    /**
     * 获取审计方信息
     *
     * @return 审计方信息
     */
    T curUser();
}
