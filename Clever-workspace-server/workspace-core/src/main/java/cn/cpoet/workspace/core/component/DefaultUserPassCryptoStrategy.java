package cn.cpoet.workspace.core.component;

import cn.cpoet.workspace.core.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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
    public boolean valid(Object user, String password) {
        UserPassCryptoBean userPassCryptoBean = getUserPassCryptoBean(user);
        return Objects.equals(userPassCryptoBean.getPassword(), encode(userPassCryptoBean, password));
    }

    @Override
    public String encode(Object user, String password) {
        String salt = getUserPassCryptoBean(user).getSalt();
        return Md5Util.md5hex((password == null ? "" : password) + DEFAULT_SALT + (salt == null ? DEFAULT_SALT : salt));
    }

    private UserPassCryptoBean getUserPassCryptoBean(Object user) {
        if (user instanceof UserPassCryptoBean) {
            return (UserPassCryptoBean) user;
        }
        UserPassCryptoBean userPassCryptoBean = new UserPassCryptoBean();
        BeanUtils.copyProperties(user, userPassCryptoBean);
        return userPassCryptoBean;
    }
}
