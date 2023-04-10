package cn.cpoet.blog.api.context;

import cn.cpoet.blog.api.constant.SubjectType;

/**
 * 主体
 *
 * @author CPoet
 */
public interface Subject {
    /**
     * 获取当前主体id
     *
     * @return 主体id
     */
    Long getId();

    /**
     * 获取当前主体姓名
     *
     * @return 主体姓名
     */
    String getName();

    /**
     * 获取当前用户名
     *
     * @return 用户名
     */
    String getUserName();

    /**
     * 获取当前用户组id
     *
     * @return 用户组id
     */
    Long getGroupId();

    /**
     * 获取当前用户组名称
     *
     * @return 用户组名称
     */
    String getGroupName();

    /**
     * 获取当前主体类型
     *
     * @return 主体类型
     */
    SubjectType type();
}
