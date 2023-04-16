package cn.cpoet.blog.starter.api.admin.service;

import cn.cpoet.blog.api.context.Subject;
import cn.cpoet.blog.starter.api.admin.vo.MenuNodeVO;
import reactor.core.publisher.Flux;

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
    Flux<MenuNodeVO> listMenu(Subject subject);
}
