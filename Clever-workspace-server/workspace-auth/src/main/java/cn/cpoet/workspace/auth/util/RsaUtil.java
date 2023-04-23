package cn.cpoet.workspace.auth.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author CPoet
 */
public abstract class RsaUtil {

    private final static String ALGORITHM_NAME = "RSA";

    private RsaUtil() {
    }

    public static KeyPair randomKeyPair() {
        try {
            final KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_NAME);
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("获取随机Key失败", e);
        }
    }

    public static KeyPair generateKeyPair(String publicKey, String privateKey) {
        return new KeyPair(generatePublicKey(publicKey), generatePrivateKey(privateKey));
    }

    public static PublicKey generatePublicKey(String publicKey) {
        byte[] encPubKey = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec encPubKeySpec = new X509EncodedKeySpec(encPubKey);
        try {
            return KeyFactory.getInstance(ALGORITHM_NAME).generatePublic(encPubKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("解析公钥失败", e);
        }
    }

    public static PrivateKey generatePrivateKey(String privateKey) {
        byte[] encPriKey = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec encPriKeySpec = new PKCS8EncodedKeySpec(encPriKey);
        try {
            return KeyFactory.getInstance(ALGORITHM_NAME).generatePrivate(encPriKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("解析私钥失败", e);
        }
    }
}
