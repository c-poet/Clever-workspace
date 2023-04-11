package cn.cpoet.blog.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

/**
 * @author CPoet
 */
@Data
@Schema(title = "记录型实体")
@EnableReactiveMongoAuditing()
public abstract class BaseRcEntity implements Entity<Long> {

    private static final long serialVersionUID = -2194295669512188184L;

    @MongoId
    @Schema(title = "主键")
    private Long id;

    @Schema(title = "创建用户")
    @JsonIgnoreProperties(allowGetters = true)
    @CreatedBy
    private Long createUser;

    @Schema(title = "创建时间")
    @JsonIgnoreProperties(allowGetters = true)
    @CreatedDate
    private LocalDateTime createTime;

    @JsonIgnore
    @Schema(title = "软删除标识")
    private Boolean deleted = Boolean.FALSE;
}
