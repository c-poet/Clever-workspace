package cn.cpoet.workspace.core.component;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户密码
 *
 * @author CPoet
 */
@Data
public class UserPassCryptoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;
}
