package cn.cpoet.workspace.model.base;

import cn.cpoet.workspace.api.annotation.Editable;
import cn.cpoet.workspace.api.validation.Delete;
import cn.cpoet.workspace.api.validation.Insert;
import cn.cpoet.workspace.api.validation.Update;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * @author CPoet
 */
@Data
@Schema(title = "记录型实体")
@FieldNameConstants
public abstract class BaseRcEntity implements Entity<Long> {

    private static final long serialVersionUID = -2194295669512188184L;

    @Schema(title = "主键")
    @MongoId(FieldType.INT64)
    @Null(message = "不允许的ID", groups = {Insert.class})
    @NotNull(message = "ID不能为空", groups = {Update.class, Delete.class})
    @Editable(value = false)
    private Long id;

    @Schema(title = "创建用户")
    @JsonIgnoreProperties(allowGetters = true)
    @CreatedBy
    @Editable(value = false)
    private Long createUser;

    @Schema(title = "创建时间")
    @JsonIgnoreProperties(allowGetters = true)
    @CreatedDate
    @Editable(value = false)
    private LocalDateTime createTime;
}