package cn.cpoet.blog.api.context;


import java.util.Map;

/**
 * 认证管理
 *
 * @author wanggf
 */
public interface AuthContext {
    /**
     * 获取当前操作主体
     *
     * @return 当前主体
     */
    Subject curSubject();

    /**
     * 授权
     *
     * @param claims 授权信息
     * @return 授权凭证
     */
    String authorize(Map<String, Object> claims);
}
