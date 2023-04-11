package cn.cpoet.blog.auth.subject;

import cn.cpoet.blog.api.constant.SubjectType;
import cn.cpoet.blog.api.context.AuthContext;
import cn.cpoet.blog.api.context.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 主体代理
 *
 * @author CPoet
 */
@Component
@RequiredArgsConstructor
public class ProxySubject implements Subject {

    private final AuthContext authContext;

    @Override
    public Long getId() {
        return getTarget().getId();
    }

    @Override
    public String getName() {
        return getTarget().getName();
    }

    @Override
    public String getUserName() {
        return getTarget().getUserName();
    }

    @Override
    public Long getGroupId() {
        return getTarget().getGroupId();
    }

    @Override
    public String getGroupName() {
        return getTarget().getGroupName();
    }

    @Override
    public SubjectType type() {
        return getTarget().type();
    }

    public Subject getTarget() {
        return authContext.curSubject();
    }

    @Override
    public String toString() {
        return getTarget().toString();
    }
}
