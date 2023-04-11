package cn.cpoet.blog.auth.context;

import cn.cpoet.blog.auth.configuration.auth.AuthProperties;
import cn.cpoet.blog.auth.util.RsaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

/**
 * @author CPoet
 */
@Component
@RequiredArgsConstructor
public class KeyPairHolder implements InitializingBean {
    private KeyPair keyPair;
    private final AuthProperties authProperties;

    public KeyPair getKeyPair() {
        return keyPair;
    }

    @Override
    public void afterPropertiesSet() {
        final String publicKey = authProperties.getPublicKey();
        final String privateKey = authProperties.getPrivateKey();
        keyPair = RsaUtil.generateKeyPair(publicKey, privateKey);
    }
}
