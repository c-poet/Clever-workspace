package cn.cpoet.workspace.auth.subject;

import cn.cpoet.workspace.api.constant.SubjectType;
import cn.cpoet.workspace.api.constant.SystemConst;
import cn.cpoet.workspace.api.context.Subject;
import cn.cpoet.workspace.auth.exception.NotAuthException;

/**
 * @author CPoet
 */
public class SystemSubject implements Subject {

    public final static SystemSubject INSTANCE = new SystemSubject();

    SystemSubject() {
    }

    @Override
    public Long getId() {
        return SystemConst.SYSTEM_USER_ID;
    }

    @Override
    public Long getTenantId() {
        return SystemConst.SYSTEM_TENANT_ID;
    }

    @Override
    public String getName() {
        throw notAuthException();
    }

    @Override
    public String getUserName() {
        throw notAuthException();
    }

    @Override
    public Long getGroupId() {
        throw notAuthException();
    }

    @Override
    public SubjectType type() {
        return SubjectType.SYSTEM;
    }

    protected NotAuthException notAuthException() {
        return new NotAuthException();
    }
}
