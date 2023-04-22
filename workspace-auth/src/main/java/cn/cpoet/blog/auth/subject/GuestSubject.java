package cn.cpoet.blog.auth.subject;

import cn.cpoet.blog.api.constant.SubjectType;

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
