package cn.cpoet.workspace.api.constant;

import cn.cpoet.workspace.api.core.Status;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 常用状态定义
 *
 * @author CPoet
 */
@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum StatusConst implements Status {
    /**
     * 系统错误
     */
    ERROR("-1", "系统错误"),
    /**
     * 成功
     */
    OK("0", "OK"),
    /**
     * 失败
     */
    FAILED("1000", "操作失败"),
    /**
     * 认证失败
     */
    AUTH_FAILED("1001", "认证失败"),
    /**
     * 用户被禁用
     */
    USER_ACCOUNT_DISABLED("1002", "用户已被禁用"),
    /**
     * 账号被锁定
     */
    USER_ACCOUNT_LOCKED("1003", "用户账号被锁定");

    private final String code;
    private final String message;
}
