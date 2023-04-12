package cn.cpoet.blog.core.component;

import cn.cpoet.blog.core.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 默认用户密码加载策略
 *
 * @author CPoet
 */
@Slf4j
@Component
public class DefaultUserPassCryptoStrategy implements UserPassCryptoStrategy {

    private final static String DEFAULT_SALT = "##2023##04##12##";

    @Override
    public boolean valid(UserPassCryptoBean user, String password) {
        return Objects.equals(user.getPassword(), encode(user, password));
    }

    @Override
    public String encode(UserPassCryptoBean user, String password) {
        String salt = user.getSalt();
        return Md5Util.md5hex((password == null ? "" : password) + DEFAULT_SALT + (salt == null ? DEFAULT_SALT : salt));
    }
}
