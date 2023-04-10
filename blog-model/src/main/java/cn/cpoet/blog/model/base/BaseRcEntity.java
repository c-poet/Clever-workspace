package cn.cpoet.blog.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author CPoet
 */
@Data
@Schema(title = "记录型实体")
public abstract class BaseRcEntity implements Entity<Long> {

    private static final long serialVersionUID = -2194295669512188184L;

    @Schema(title = "主键")
    private Long id;

    @Schema(title = "创建用户")
    @JsonIgnoreProperties(allowGetters = true)
    private Long createUser;

    @Schema(title = "创建时间")
    @JsonIgnoreProperties(allowGetters = true)
    private LocalDateTime createTime;

    @JsonIgnore
    @Schema(title = "软删除标识")
    private Boolean deleted = Boolean.FALSE;
}
