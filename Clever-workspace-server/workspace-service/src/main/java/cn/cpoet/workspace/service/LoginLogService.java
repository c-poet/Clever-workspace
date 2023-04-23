package cn.cpoet.workspace.service;

import cn.cpoet.workspace.model.domain.LoginLog;

/**
 * @author CPoet
 */
public interface LoginLogService extends BaseService<LoginLog> {
    /**
     * 保存登录日志
     *
     * @param loginLog 登录日志
     */
    void saveLog(LoginLog loginLog);
}
