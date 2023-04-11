package cn.cpoet.blog.auth.support;

import cn.cpoet.blog.api.annotation.Accessor;
import cn.cpoet.blog.api.constant.SubjectType;
import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.api.core.AccessorHandler;
import cn.cpoet.blog.api.core.AccessorMeta;
import cn.cpoet.blog.auth.exception.AuthException;

import java.lang.reflect.Method;

/**
 * 默认访问处理器
 *
 * @author CPoet
 */
public class DefaultAccessorHandler implements AccessorHandler {

    public final static AccessorHandler INSTANCE = new DefaultAccessorHandler();

    @Override
    public void handle(Subject subject, Object obj, Method method, AccessorMeta meta, Accessor accessor) {
        SubjectType[] subjects = accessor.subjects();
        if (subjects.length != 0) {
            boolean targetSubject = false;
            for (SubjectType subjectType : subjects) {
                if (subject.type() == subjectType) {
                    targetSubject = true;
                    break;
                }
            }
            if (!targetSubject) {
                throw new AuthException("非目标主体类型");
            }
        }
        String[] val = accessor.value();
        if (val.length != 0) {

        }
    }
}
