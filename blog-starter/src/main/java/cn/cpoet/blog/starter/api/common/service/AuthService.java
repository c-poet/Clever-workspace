package cn.cpoet.blog.starter.api.common.service;

import cn.cpoet.blog.starter.api.common.dto.LoginDTO;
import cn.cpoet.blog.starter.api.common.vo.LoginVO;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
public interface AuthService {
    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @param exchange 请求上下文
     * @return 返回身份凭证
     */
    Mono<LoginVO> login(LoginDTO loginDTO, ServerWebExchange exchange);
}
