package cn.cpoet.workspace.module.admin.vo;

import cn.cpoet.workspace.model.domain.Permission;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author CPoet
 */
@Data
@Schema(title = "菜单列表")
public class MenuNodeVO {
    @Schema(title = "菜单id")
    private Long id;

    @Schema(title = "父级id")
    private Long parentId;

    @Schema(title = "编码")
    private String code;

    @Schema(title = "名称/标题")
    private String name;

    @Schema(title = "图标")
    private String icon;

    @Schema(title = "访问path")
    private String path;

    @Schema(title = "访问url")
    private String url;

    @Schema(title = "徽标")
    private String badge;

    @Schema(title = "isSingle")
    private Boolean isSingle;

    @Schema(title = "是否隐藏")
    private Boolean hidden;

    @Schema(title = "是否固定")
    private Boolean affix;

    @Schema(title = "是否缓存")
    private Boolean cacheable;

    @Schema(title = "权限描述")
    private String description;

    @Schema(title = "子菜单")
    private List<MenuNodeVO> children;

    public static MenuNodeVO of(Permission permission) {
        MenuNodeVO menuNodeVO = new MenuNodeVO();
        BeanUtils.copyProperties(permission, menuNodeVO);
        return menuNodeVO;
    }
}
