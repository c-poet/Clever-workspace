package cn.cpoet.blog.starter.api.admin.vo;

import cn.cpoet.blog.model.domain.Group;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author CPoet
 */
@Data
@Schema(title = "用户组")
public class GroupNodeVO extends Group {
    @Schema(title = "下级用户组")
    private List<GroupNodeVO> children;

    public static GroupNodeVO of(Group group) {
        GroupNodeVO groupNodeVO = new GroupNodeVO();
        BeanUtils.copyProperties(group, groupNodeVO);
        return groupNodeVO;
    }
}
