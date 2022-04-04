package com.lzy.common.lang;

import lombok.Data;
import java.io.Serializable;


@Data  // 使用@Data注解省去Getter Setter的工作
public class Result implements Serializable
{

    private int code;  // 200表示正常，非200表示异常情况
    private String msg;
    private Object data;

    // 下面为需要的静态方法

    // 进一步进行封装（重载）
    public static Result success(Object data)
    {
        // 对于success情况，msg的内容没有那么重要
        // 调用下方的success方法
        return success(200, "Successful Operation.", data);
    }

    public static Result success(int code, String msg, Object data)
    {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail(String msg)
    {
        return fail(400, msg, null);
    }

    public static Result fail(String msg, Object data)
    {
        return fail(400, msg, data);
    }

    public static Result fail(int code, String msg, Object data)
    {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}

