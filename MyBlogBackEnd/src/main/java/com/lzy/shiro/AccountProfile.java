package com.lzy.shiro;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AccountProfile implements Serializable
{
    private Long userId;
    private String username;
    private String avatar;
    private String email;

    public Long getId()
    {
        return userId;
    }
}
