package cn.cpoet.blog.auth.subject;

import cn.cpoet.blog.api.constant.SubjectType;
import cn.cpoet.blog.auth.constant.AuthSubjectConst;
import cn.cpoet.blog.auth.util.TypeUtil;

import java.util.Map;

/**
 * @author CPoet
 */
public class AuthSubject extends GuestSubject {

    private final Long id;
    private final String name;
    private final String username;
    private final Long groupId;

    public AuthSubject(Map<String, Object> claims) {
        id = TypeUtil.toLong(claims.get(AuthSubjectConst.USER_ID));
        name = TypeUtil.toString(claims.get(AuthSubjectConst.NAME));
        username = TypeUtil.toString(claims.get(AuthSubjectConst.USER_NAME));
        groupId = TypeUtil.toLong(claims.get(AuthSubjectConst.GROUP_ID));
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public Long getGroupId() {
        return groupId;
    }

    @Override
    public SubjectType type() {
        return SubjectType.AUTH;
    }
}
