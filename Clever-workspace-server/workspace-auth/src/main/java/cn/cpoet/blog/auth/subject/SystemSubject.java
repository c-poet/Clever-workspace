package cn.cpoet.blog.auth.subject;

import cn.cpoet.blog.api.constant.SubjectType;
import cn.cpoet.blog.api.constant.SystemConst;
import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.auth.exception.NotAuthException;

import java.util.Collections;
import java.util.Set;

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
    public Set<String> getPermissions() {
        return Collections.emptySet();
    }

    @Override
    public SubjectType type() {
        return SubjectType.SYSTEM;
    }

    protected NotAuthException notAuthException() {
        return new NotAuthException();
    }
}
