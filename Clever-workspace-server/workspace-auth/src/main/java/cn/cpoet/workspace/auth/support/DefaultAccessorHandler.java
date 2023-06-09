package cn.cpoet.workspace.auth.support;

import cn.cpoet.workspace.api.annotation.Accessor;
import cn.cpoet.workspace.api.constant.SubjectType;
import cn.cpoet.workspace.api.context.Subject;
import cn.cpoet.workspace.api.core.AccessorHandler;
import cn.cpoet.workspace.api.core.AccessorMeta;
import cn.cpoet.workspace.auth.exception.AuthException;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Set;

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
        String[] values = accessor.value();
        if (values.length != 0) {
            Set<String> permissions = subject.getPermissions();
            if (CollectionUtils.isEmpty(permissions)) {
                throw new AuthException("权限不足");
            }

        }
    }
}
