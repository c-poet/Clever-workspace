package cn.cpoet.workspace.model.domain;

import cn.cpoet.workspace.api.annotation.Editable;
import cn.cpoet.workspace.api.validation.Insert;
import cn.cpoet.workspace.api.validation.Update;
import cn.cpoet.workspace.model.base.TenantEntity;
import cn.cpoet.workspace.model.constant.SexEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author CPoet
 */
@Data
@Entity
@Editable
@Schema(title = "用户")
@Table(name = "sys_user")
public class User extends TenantEntity {
    private static final long serialVersionUID = 1L;

    @Schema(title = "姓名")
    @NotEmpty(message = "姓名不能为空", groups = {Insert.class, Update.class,})
    @Length(min = 1, max = 8, message = "姓名长度在${min}-${max}之间", groups = {Insert.class, Update.class})
    @Column(name = "name")
    private String name;

    @Schema(title = "昵称")
    @NotEmpty(message = "昵称不能为空", groups = {Insert.class, Update.class})
    @Length(min = 1, max = 48, message = "昵称长度在${min}-${max}之间", groups = {Insert.class, Update.class})
    @Column(name = "nick_name")
    private String nickName;

    @Schema(title = "用户名")
    @NotEmpty(message = "用户名不能为空", groups = {Insert.class, Update.class})
    @Pattern(regexp = "^[a-zA-Z_][0-9a-zA-Z_]+$", message = "用户名由字母、数字及下划线组成，且首字符不能为数字", groups = {Insert.class, Update.class})
    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Editable(value = false)
    @Schema(title = "密码")
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Editable(value = false)
    @Schema(title = "盐值")
    @Column(name = "salt")
    private String salt;

    @Schema(title = "头像地址")
    @Column(name = "avatar")
    private String avatar;

    @Schema(title = "邮箱")
    @NotEmpty(message = "邮箱不能为空", groups = {Insert.class, Update.class})
    @Email(message = "邮箱格式不正确", groups = {Insert.class, Update.class})
    @Column(name = "email")
    private String email;

    @Schema(title = "手机号")
    @Column(name = "mobile")
    private String mobile;

    @Schema(title = "性别")
    @NotNull(message = "性别不能为空", groups = {Insert.class, Update.class})
    @Column(name = "sex")
    private SexEnum sex;

    @Schema(title = "用户组id")
    @NotNull(message = "用户组不能为空", groups = {Insert.class, Update.class})
    @Column(name = "group_id")
    private Long groupId;

    @Editable(value = false)
    @Schema(title = "是否内置")
    @Column(name = "build_in")
    private Boolean buildIn;

    @Schema(title = "是否锁定")
    @Column(name = "locked")
    private Boolean locked;

    @Schema(title = "是否启用")
    @Column(name = "enabled")
    private Boolean enabled;
}
