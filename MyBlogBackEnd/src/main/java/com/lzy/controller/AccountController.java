package com.lzy.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzy.common.dto.LoginDto;
import com.lzy.common.lang.Result;
import com.lzy.entity.Users;
import com.lzy.service.UsersService;
import com.lzy.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AccountController
{
    @Autowired
    UsersService usersService;

    @Autowired
    JwtUtils jwtUtils;

    // 登录接口
    @PostMapping("/login")
    // 仍需要通过@Validated进行校验
    public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response)
    {
        Users users = usersService.getOne(new QueryWrapper<Users>().eq("username", loginDto.getUsername()));
        // 使用断言进行处理（如果异常会抛出异常 IllegalArgumentException）
        Assert.notNull(users, "The user is not exist.");

        //if(!users.getUserPassword().equals(SecureUtil.md5(loginDto.getPassword())))
        if(!users.getUserPassword().equals(loginDto.getPassword()))
        {
            return Result.fail("Wrong password.");
        }
        System.out.println("Correct password");
        System.out.println(users.getUserId());
        // LZY test (above is correct)

        String jwt = jwtUtils.generateToken(users.getUserId());  // error in this line

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");
        // 返回map信息
        return Result.success(MapUtil.builder()
                .put("userId", users.getUserId())
                .put("username", users.getUsername())
                .put("avatar", users.getAvatar())
                .put("email", users.getEmail())
                .map()
        );
    }


    // 退出接口
    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout()
    {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }
}
