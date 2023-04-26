package cn.cpoet.workspace.model.base;

import cn.cpoet.workspace.api.annotation.Editable;
import cn.cpoet.workspace.api.context.AppContextHolder;
import cn.cpoet.workspace.api.core.IdTool;
import cn.cpoet.workspace.api.validation.Delete;
import cn.cpoet.workspace.api.validation.Insert;
import cn.cpoet.workspace.api.validation.Update;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tk.mybatis.mapper.annotation.LogicDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

/**
 * @author CPoet
 */
@Data
@MappedSuperclass
@Schema(title = "记录型实体")
public abstract class BaseRcEntity implements Entity<Long> {

    private static final long serialVersionUID = -2194295669512188184L;

    @Id
    @Column(name = "id")
    @Schema(title = "主键")
    @Null(message = "不允许的ID", groups = {Insert.class})
    @NotNull(message = "ID不能为空", groups = {Update.class, Delete.class})
    @Editable(value = false)
    private Long id;

    @Column(name = "create_user")
    @Schema(title = "创建用户")
    @JsonIgnoreProperties(allowGetters = true)
    @Editable(value = false)
    private Long createUser;

    @Column(name = "create_time")
    @Schema(title = "创建时间")
    @JsonIgnoreProperties(allowGetters = true)
    @Editable(value = false)
    private LocalDateTime createTime;

    @LogicDelete
    @Column(name = "deleted")
    @Schema(title = "是否删除")
    @Editable(value = false)
    private Boolean deleted;

    @PostPersist
    public void onInsert() {
        id  = IdTool.nextId();
        createUser = AppContextHolder.getAuthContext().curSubject().getId();
        createTime = LocalDateTime.now();
        deleted = Boolean.FALSE;
    }

    @PostUpdate
    public void onUpdate() {
    }
}
