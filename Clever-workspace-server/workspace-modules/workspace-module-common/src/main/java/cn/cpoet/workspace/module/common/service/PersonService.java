package cn.cpoet.workspace.module.common.service;

import cn.cpoet.workspace.api.context.Subject;
import cn.cpoet.workspace.model.domain.User;

/**
 * @author CPoet
 */
public interface PersonService {
    /**
     * 获取当前用户信息
     *
     * @param subject 登录主体
     * @return 用户信息
     */
    User getPersonInfo(Subject subject);
}
