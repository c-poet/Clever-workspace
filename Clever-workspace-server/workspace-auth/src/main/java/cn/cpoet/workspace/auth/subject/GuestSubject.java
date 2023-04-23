package cn.cpoet.workspace.auth.subject;

import cn.cpoet.workspace.api.constant.SubjectType;

/**
 * @author CPoet
 */
public class GuestSubject extends SystemSubject {

    public final static GuestSubject INSTANCE = new GuestSubject();

    GuestSubject() {
    }

    @Override
    public SubjectType type() {
        return SubjectType.GUEST;
    }
}
