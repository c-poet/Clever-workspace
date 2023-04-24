package cn.cpoet.workspace.model.base;

import cn.cpoet.workspace.api.annotation.Editable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

/**
 * @author CPoet
 */
@Data
@MappedSuperclass
@Schema(title = "操作型实体")
public abstract class BaseEntity extends BaseRcEntity {

    private static final long serialVersionUID = -3243328282267904618L;

    @Version
    @JsonIgnore
    @Column(name = "version")
    @Schema(title = "数据版本号")
    @Editable(value = false)
    private Integer version;

    @Schema(title = "更新用户")
    @JsonIgnoreProperties(allowGetters = true)
    @Editable(value = false)
    @Column(name = "update_user")
    private Long updateUser;

    @Schema(title = "更新时间")
    @JsonIgnoreProperties(allowGetters = true)
    @Editable(value = false)
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
