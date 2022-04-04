package com.lzy.shiro;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.lzy.common.lang.Result;
import com.lzy.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends AuthenticatingFilter
{
    // 需要重写两个方法

    @Autowired
    JwtUtils jwtUtils;

    @Override
    // shiro将jwt封装为token
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception
    {

        HttpServletRequest request = (HttpServletRequest)servletRequest;  // 强制类型转换
        String jwt = request.getHeader("Authorization");
        if(StringUtils.isEmpty(jwt))  // 说明没有登录
        {
            return null;
        }
        return new JwtToken(jwt);
    }

    @Override
    // 拦截
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception
    {
        // 判断jwt是否过期
        HttpServletRequest request = (HttpServletRequest)servletRequest;  // 强制类型转换
        String jwt = request.getHeader("Authorization");
        if(StringUtils.isEmpty(jwt))  // 说明没有登录，直接交给注解进行拦截
        {
            return true;
        }
        else
        {
            // 校验jwt
            Claims claim = jwtUtils.getClaimByToken(jwt);
            if(claim==null||jwtUtils.isTokenExpired(claim.getExpiration()))  //jwt不正常则抛出异常
            {
                throw new ExpiredCredentialsException("Your token is out-of-date, please login again.");
            }

            // 执行登录
            return executeLogin(servletRequest,servletResponse);
        }
    }

    @Override
    // 执行登录出现异常
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
                                     ServletRequest request, ServletResponse response)
    {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        Throwable throwable = e.getCause() == null ? e : e.getCause();
        Result result = Result.fail(throwable.getMessage());
        String json = JSONUtil.toJsonStr(result); // hutool依赖中的

        try
        {
            httpServletResponse.getWriter().print(json);
        }
        catch (IOException ioException)
        {
            // ioException.printStackTrace();
        }
        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception
    {

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name()))
        {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }

        return super.preHandle(request, response);
    }
}
    //
    // @Autowired
    // JwtUtils jwtUtils;
    //
    // @Override
    // protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
    //
    //     HttpServletRequest request = (HttpServletRequest) servletRequest;
    //     String jwt = request.getHeader("Authorization");
    //     if(StringUtils.isEmpty(jwt)) {
    //         return null;
    //     }
    //
    //     return new JwtToken(jwt);
    // }
    //
    // @Override
    // protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
    //
    //     HttpServletRequest request = (HttpServletRequest) servletRequest;
    //     String jwt = request.getHeader("Authorization");
    //     if(StringUtils.isEmpty(jwt)) {
    //         return true;
    //     } else {
    //
    //         // 校验jwt
    //         Claims claim = jwtUtils.getClaimByToken(jwt);
    //         if(claim == null || jwtUtils.isTokenExpired(claim.getExpiration())) {
    //             throw new ExpiredCredentialsException("token已失效，请重新登录");
    //         }
    //
    //         // 执行登录
    //         return executeLogin(servletRequest, servletResponse);
    //     }
    // }
    //
    // @Override
    // protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
    //
    //     HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    //
    //     Throwable throwable = e.getCause() == null ? e : e.getCause();
    //     Result result = Result.fail(throwable.getMessage());
    //     String json = JSONUtil.toJsonStr(result);
    //
    //     try {
    //         httpServletResponse.getWriter().print(json);
    //     } catch (IOException ioException) {
    //
    //     }
    //     return false;
    // }
    //


