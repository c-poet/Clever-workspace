package cn.cpoet.blog.starter.common.service;

import cn.cpoet.blog.starter.common.dto.LoginDTO;
import cn.cpoet.blog.starter.common.vo.LoginVO;
import reactor.core.publisher.Mono;

/**
 * @author CPoet
 */
public interface AuthService {
    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 返回身份凭证
     */
    Mono<LoginVO> login(LoginDTO loginDTO);
}
