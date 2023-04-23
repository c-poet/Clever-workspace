package cn.cpoet.workspace.module.common.service;

import cn.cpoet.workspace.module.common.dto.LoginDTO;
import cn.cpoet.workspace.module.common.vo.LoginVO;

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
    LoginVO login(LoginDTO loginDTO);
}
