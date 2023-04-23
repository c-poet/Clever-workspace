package cn.cpoet.blog.core.service;

import cn.cpoet.blog.model.domain.LoginLog;

import java.util.List;

/**
 * @author CPoet
 */
public interface LoginLogService {
    /**
     * 保存登录日志
     *
     * @param loginLog 登录日志
     * @return 保存结果
     */
    LoginLog saveLog(LoginLog loginLog);

    /**
     * 查询用户最近一次登录日志
     *
     * @param userId 用户id
     * @return 登录日志
     */
    LoginLog findLastLog(Long userId);

    /**
     * 查询用户最近一次登录日志
     *
     * @param userIds 用户Id列表
     * @return 登录日志
     */
    List<LoginLog> findLastLog(List<Long> userIds);
}
