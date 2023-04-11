package cn.cpoet.blog.auth.context;

import cn.cpoet.blog.api.context.AppContextHolder;
import cn.cpoet.blog.api.context.AuthContext;
import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.api.context.WebContext;
import cn.cpoet.blog.auth.configuration.auth.AuthProperties;
import cn.cpoet.blog.auth.constant.AuthSubjectConst;
import cn.cpoet.blog.auth.subject.AuthSubject;
import cn.cpoet.blog.auth.subject.GuestSubject;
import cn.cpoet.blog.auth.subject.SystemSubject;
import cn.cpoet.blog.auth.util.JwtUtil;
import cn.cpoet.blog.auth.util.TypeUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
        WebContext webContext = AppContextHolder.getWebContext();
        if (webContext == null) {
            return SystemSubject.INSTANCE;
        }
        Subject subject = webContext.getAttr(AUTH_SUBJECT_REQS_CACHE);
        if (subject != null) {
            return subject;
        }
        String token = webContext.getHeader(authProperties.getAuthenticationHeader());
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
        webContext.setAttr(AUTH_SUBJECT_REQS_CACHE, subject);
        return subject;
    }

    @Override
    public String authorize(Map<String, Object> claims) {
        PrivateKey key = keyPairHolder.getKeyPair().getPrivate();
        Duration tokenTtl = authProperties.getTokenTtl();
        String userName = TypeUtil.toString(AuthSubjectConst.USER_NAME);
        return JwtUtil.encode(key, userName, tokenTtl.toMillis(), claims);
    }
}
