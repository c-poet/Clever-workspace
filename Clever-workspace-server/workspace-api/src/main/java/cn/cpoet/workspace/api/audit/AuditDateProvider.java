package cn.cpoet.workspace.api.audit;

import java.time.LocalDateTime;

/**
 * 审计时间提供
 *
 * @author CPoet
 */
public interface AuditDateProvider {

    /**
     * 默认实例
     */
    AuditDateProvider INSTANCE = new AuditDateProvider() {
    };

    /**
     * 获取当前审计时间
     *
     * @return 审计时间
     */
    default LocalDateTime curDate() {
        return LocalDateTime.now();
    }
}
