package com.lzy.util;

import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;



@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "lzy.jwt")
public class JwtUtils
{
    private String secret;// 密钥
    private long expire;// 超时时间
    private String header;

    /**
     * 生成jwt token
     */
    public String generateToken(long userId)
    {
        // System.out.println("**********************LZY TEST**********************");
        // System.out.println(secret+"  "+expire+"  "+header);  // f4e2e52034348f86b67cde581c0f9eb5  604800  Authorization
        Date nowDate = new Date();  // 当前时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);  // 过期时间(7天) ms->s
        //System.out.println("nowDate: "+nowDate+"\n"+"expireDate: "+expireDate);  // 日期&时区正确

        // Object test = Jwts.builder()
        //         .setHeaderParam("typ", "JWT")
        //         .setSubject(userId+"")
        //         .setIssuedAt(nowDate)
        //         .setExpiration(expireDate)
        //         .signWith(SignatureAlgorithm.HS512, secret)
        //         .compact();

        // System.out.println(test);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(userId+"")
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimByToken(String token)
    {
        try
        {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e)
        {
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     */
    public boolean isTokenExpired(Date expiration)
    {
        return expiration.before(new Date());
    }
}
