package cn.cpoet.blog.starter.api.admin.controller;

import cn.cpoet.blog.api.validation.Insert;
import cn.cpoet.blog.api.validation.Update;
import cn.cpoet.blog.core.dto.IdDTO;
import cn.cpoet.blog.core.dto.IdsDTO;
import cn.cpoet.blog.core.vo.PageVO;
import cn.cpoet.blog.model.domain.User;
import cn.cpoet.blog.starter.api.admin.dto.UserDTO;
import cn.cpoet.blog.starter.api.admin.param.UserParam;
import cn.cpoet.blog.starter.api.admin.service.UserService;
import cn.cpoet.blog.starter.api.admin.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author CPoet
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "用户管理")
public class UserController {

    private final UserService userService;

    @GetMapping("/getUserById")
    @Operation(summary = "根据id查询用户")
    public User getUserById(@RequestParam Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/listUser")
    @Operation(summary = "分页查询用户")
    public PageVO<UserVO> listUser(@Validated UserParam userParam) {
        return userService.listUser(userParam);
    }

    @PostMapping("/insertUser")
    @Operation(summary = "新增用户")
    public User insertUser(@RequestBody @Validated(Insert.class) UserDTO userDTO) {
        return userService.insertUser(userDTO);
    }

    @PostMapping("/updateUser")
    @Operation(summary = "更新用户")
    public User updateUser(@RequestBody @Validated(Update.class) User user) {
        return userService.updateUser(user);
    }

    @PostMapping("/deleteUserById")
    @Operation(summary = "根据id删除用户")
    public void deleteUserById(@RequestBody @Validated IdDTO idDTO) {
        userService.deleteUserById(idDTO.getId());
    }

    @PostMapping("/deleteUserByIds")
    @Operation(summary = "批量删除用户")
    public void deleteUserByIds(@RequestBody @Validated IdsDTO idsDTO) {
        userService.deleteUserByIds(idsDTO.getIds());
    }
}
