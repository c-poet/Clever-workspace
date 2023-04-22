package cn.cpoet.blog.starter.api.common.service;

import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.model.domain.User;

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
