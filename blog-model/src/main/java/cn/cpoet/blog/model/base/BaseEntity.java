package cn.cpoet.blog.model.base;

import cn.cpoet.blog.api.annotation.Editable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

import java.time.LocalDateTime;

/**
 * @author CPoet
 */
@Data
@Schema(title = "操作型实体")
@FieldNameConstants
public abstract class BaseEntity extends BaseRcEntity {

    private static final long serialVersionUID = -3243328282267904618L;

    @JsonIgnore
    @Version
    @Schema(title = "数据版本号，采用时间戳格式")
    @Editable(value = false)
    private Long version;

    @Schema(title = "更新用户")
    @JsonIgnoreProperties(allowGetters = true)
    @LastModifiedBy
    @Editable(value = false)
    private Long updateUser;

    @Schema(title = "更新时间")
    @JsonIgnoreProperties(allowGetters = true)
    @LastModifiedDate
    @Editable(value = false)
    private LocalDateTime updateTime;
}
