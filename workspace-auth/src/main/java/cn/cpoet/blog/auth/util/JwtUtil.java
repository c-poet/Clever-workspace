package cn.cpoet.blog.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * jwt工具
 *
 * @author wanggf
 */
public abstract class JwtUtil {
    private JwtUtil() {
    }

    /**
     * 生成token
     *
     * @param key       加密key
     * @param sub       接收主体
     * @param ttlMillis token有效时长，ms为单位
     * @param claims    其它需要存储于jwt的信息
     * @return jwt
     */
    public static String encode(Key key, String sub, long ttlMillis, Map<String, Object> claims) {
        //iss签发人，ttlMillis生存时间，claims是指还想要在jwt中存储的一些非隐私信息
        long nowMillis = System.currentTimeMillis();
        JwtBuilder builder = Jwts.builder()
                // 对claims的初始化最好放在最前面
                .setClaims(claims)
                // JWT的唯一标识
                .setId(UUID.randomUUID().toString())
                // 签发时间
                .setIssuedAt(new Date(nowMillis))
                .setSubject(sub)
                .signWith(key, SignatureAlgorithm.RS256);
        if (ttlMillis >= 0) {
            // 设置jwt有效时间
            builder.setExpiration(new Date(nowMillis + ttlMillis));
        }
        return builder.compact();
    }

    /**
     * 获取jwt claims
     *
     * @param key   加密key
     * @param token token值
     * @return Claims
     */
    public static Claims decode(Key key, String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
