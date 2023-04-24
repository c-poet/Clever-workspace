package cn.cpoet.workspace.auth.context;

import cn.cpoet.workspace.api.context.AuthContext;
import cn.cpoet.workspace.api.context.Subject;
import cn.cpoet.workspace.auth.configuration.auth.AuthProperties;
import cn.cpoet.workspace.auth.constant.AuthSubjectConst;
import cn.cpoet.workspace.auth.subject.AuthSubject;
import cn.cpoet.workspace.auth.subject.GuestSubject;
import cn.cpoet.workspace.auth.subject.SystemSubject;
import cn.cpoet.workspace.auth.util.JwtUtil;
import cn.cpoet.workspace.util.TypeUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.time.Duration;
import java.util.Map;

/**
 * 简单实现认证上下文
 *
 * @author wanggf
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleAuthContext implements AuthContext {

    private final static String AUTH_SUBJECT_REQS_CACHE = "_auth_subject_reqs_cache";

    private final KeyPairHolder keyPairHolder;
    private final AuthProperties authProperties;

    @Override
    public Subject curSubject() {
        HttpServletRequest request = getContextRequest();
        if (request == null) {
            return SystemSubject.INSTANCE;
        }
        Object attr = request.getAttribute(AUTH_SUBJECT_REQS_CACHE);
        if (attr != null) {
            return (Subject) attr;
        }
        Subject subject = null;
        String token = request.getHeader(authProperties.getAuthenticationHeader());
        if (token != null) {
            try {
                final KeyPair keyPair = keyPairHolder.getKeyPair();
                Claims claims = JwtUtil.decode(keyPair.getPublic(), token);
                subject = new AuthSubject(claims);
            } catch (Exception e) {
                log.debug("解析jwt[token = {}]失败，原因：{}", e.getMessage(), e);
            }
        }
        if (subject == null) {
            subject = GuestSubject.INSTANCE;
        }
        request.setAttribute(AUTH_SUBJECT_REQS_CACHE, subject);
        return subject;
    }

    @Override
    public String authorize(Map<String, Object> claims) {
        PrivateKey key = keyPairHolder.getKeyPair().getPrivate();
        Duration tokenTtl = authProperties.getTokenTtl();
        String userName = TypeUtil.toString(claims.get(AuthSubjectConst.USER_NAME));
        return JwtUtil.encode(key, userName, tokenTtl.toMillis(), claims);
    }

    /**
     * 获取当前请求上下文中的request
     *
     * @return request
     */
    private HttpServletRequest getContextRequest() {
        RequestAttributes attr = RequestContextHolder.getRequestAttributes();
        return attr == null ? null : ((ServletRequestAttributes) attr).getRequest();
    }
}
