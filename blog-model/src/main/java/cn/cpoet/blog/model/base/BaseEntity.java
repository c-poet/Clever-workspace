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
@Schema(title = "操作型实体")
public abstract class BaseEntity extends BaseRcEntity {

    private static final long serialVersionUID = -3243328282267904618L;

    @JsonIgnore
    @Schema(title = "数据版本号，采用时间戳格式")
    private Long version;

    @Schema(title = "更新用户")
    @JsonIgnoreProperties(allowGetters = true)
    private Long updateUser;

    @Schema(title = "更新时间")
    @JsonIgnoreProperties(allowGetters = true)
    private LocalDateTime updateTime;
}
