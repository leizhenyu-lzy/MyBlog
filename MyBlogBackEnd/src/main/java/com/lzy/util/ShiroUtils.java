package com.lzy.util;


import com.lzy.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

public class ShiroUtils
{
    public static AccountProfile getProfile()  // 静态方法
    {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();  // 强制类型转换
    }
}
