package cn.cpoet.workspace.module.admin.service;

import cn.cpoet.workspace.api.context.Subject;
import cn.cpoet.workspace.module.admin.vo.MenuNodeVO;

import java.util.List;

/**
 * @author CPoet
 */
public interface PersonService {
    /**
     * 获取用户菜单
     *
     * @param subject 登录主体
     * @return 菜单列表
     */
    List<MenuNodeVO> listMenu(Subject subject);
}
