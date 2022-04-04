package com.lzy.controller;


import com.lzy.common.lang.Result;
import com.lzy.entity.Users;
import com.lzy.service.UsersService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liuzhiyu
 * @since 2022-03-31
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService usersService;

    @RequiresAuthentication
    @GetMapping("/{user_id}")
    public Object index(@PathVariable("user_id") Long user_id)
    {
        Users user = usersService.getById(user_id);
        return Result.success(user);
    }

    @PostMapping("/verify")
    public Result verify(@Validated @RequestBody Users users)
    {
        return Result.success(users);
    }
}
