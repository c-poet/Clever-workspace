package cn.cpoet.workspace.core.component;

/**
 * 用户密码加载策略
 *
 * @author CPoet
 */
public interface UserPassCryptoStrategy {
    /**
     * 验证用户密码是否正确
     *
     * @param user     用户信息
     * @param password 密码
     * @return 是否验证通过
     */
    boolean valid(Object user, String password);

    /**
     * 对用户密码进行编码
     *
     * @param user     用户信息
     * @param password 明文密码
     * @return 加密后的密码
     */
    String encode(Object user, String password);
}
