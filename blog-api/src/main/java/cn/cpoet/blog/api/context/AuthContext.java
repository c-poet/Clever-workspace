package cn.cpoet.blog.api.context;


import org.springframework.web.server.ServerWebExchange;
import reactor.util.context.ContextView;

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
     * @param contextView 上下文
     * @return 当前主体
     */
    Subject curSubject(ContextView contextView);

    /**
     * 授权
     *
     * @param claims 授权信息
     * @return 授权凭证
     */
    String authorize(Map<String, Object> claims);
}
