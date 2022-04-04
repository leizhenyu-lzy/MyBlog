package com.lzy.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.lzy.entity.Users;
import com.lzy.service.UsersService;
// import com.lzy.util.JwtUtils;
import com.lzy.util.JwtUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRealm extends AuthorizingRealm
{
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UsersService usersService;

    @Override
    public boolean supports(AuthenticationToken token)
    {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection)
    {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException
    {
        JwtToken jwtToken = (JwtToken) authenticationToken;
        // 进行强制类型转换
        String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();

        Users users = usersService.getById(Long.valueOf(userId));

        if (users == null)  // 用户不存在，抛出异常
        {
            throw new UnknownAccountException("账户不存在");
        }

        if (users.getStatus() == -1)  // 用户被锁定，抛出异常
        {
            throw new LockedAccountException("账户已被锁定");
        }

        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(users, profile);  // 将相关信息进行copy

        //return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());

    }
}
    //
    // @Autowired
    // JwtUtils jwtUtils;
    //
    // @Autowired
    // UsersService usersService;
    //
    // @Override
    // public boolean supports(AuthenticationToken token)
    // {
    //     return token instanceof JwtToken;
    // }
    //
    // @Override
    // protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals)
    // {
    //     return null;
    // }
    //
    // @Override
    // protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException
    // {
    //
    //     JwtToken jwtToken = (JwtToken) token;
    //
    //     String userId = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal()).getSubject();
    //
    //     Users users = usersService.getById(Long.valueOf(userId));
    //     if (users == null) {
    //         throw new UnknownAccountException("账户不存在");
    //     }
    //
    //     if (users.getStatus() == -1) {
    //         throw new LockedAccountException("账户已被锁定");
    //     }
    //
    //     AccountProfile profile = new AccountProfile();
    //     BeanUtil.copyProperties(users, profile);
    //
    //     return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    // }
// }
