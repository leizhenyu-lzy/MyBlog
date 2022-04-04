package com.lzy.common.exception;

import com.lzy.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
{
    // 捕获shiro异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e)
    {
        log.error("ShiroException:----------------{}", e);
        return Result.fail(401, e.getMessage(), null);
    }

    // 实体校验异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e)
    {
        log.error("MethodArgumentNotValidException:----------------{}", e);
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();

        return Result.fail(objectError.getDefaultMessage());
    }

    // Assert异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e)
    {
        log.error("IllegalArgumentException：----------------{}", e);
        return Result.fail(e.getMessage());
    }

    // 捕获运行时异常
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 返回状态码
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e)
    {
        log.error("RuntimeException:----------------{}", e); // 日志输出
        return Result.fail(e.getMessage());
    }
}
