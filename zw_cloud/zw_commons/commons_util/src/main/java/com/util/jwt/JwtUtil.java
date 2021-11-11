package com.util.jwt;

import com.util.constant.auth.TokenConstant;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * JwtUtil
 *
 * @author Chill
 */
@Slf4j
public class JwtUtil {

    public static Integer AUTH_LENGTH = 7;

    public static String BEARER = TokenConstant.BEARER;

    public static String SIGN_KEY = TokenConstant.SIGN_KEY;

    public static String BASE64_SECURITY = Base64.getEncoder().encodeToString(SIGN_KEY.getBytes(StandardCharsets.UTF_8));

    /**
     * 密钥 -- 根据实际项目，这里可以做成配置
     */
    public static final String KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANtQrgZDl17w4RvdN6VB3DBVuUZd8XVzUg8XqdK/z5If3wTJ36Re0Oji7Yqk3bDJx7a+8a0RmAN6YXhP+Ea/vZR6nQiNS3Cxp8sPY/Bci1+GmsJn2sBpPeCYLu3cJTPxuPlcgs+elYCortO52xSWB8Yb7kIV+LYipoiKVQrx6nUnAgMBAAECgYEAuZtGLDb33HmviY1mko1yc9WYv1yyBNxcDjnQl3ONj/U5ISxYARvdYbrrnfJ5TCz4wEhI8Fd7dzYTV2cWj4mmPnkPZ2XbpAqLeKJdHdVsIOhpCQA4WLKONa2KtdVacfmtf1H+OP+fPvZ1EckN+qNNixh5FAGwDv0m3RTdpxQg9UECQQD5YUNnrtM3WB/lv72d7CMCtB6uLJ4uvZTJH0qaZ4yGyN3TDbb8BNp0De6b+w7CqmQN+4DRqd6A41qeE5obp1ErAkEA4SMaKRsJfcjN6z+wF7/iut+BrMATL8xJUUsSNzdJnAzngx4f2GZ80svDfKAWuzcP+s1K33v/j36ZgTKnEkPV9QJBAPTh2NHgr9JB/Bicv3NrJToLa7E3aSGW0pro2lZi5nCYoJMWfaBel7Gs2Klc0DLCJTxBw06fUzoXh62ArC0JkT8CQApyV4dqwsD8bVmqJeCkPcAQGPNMIlfIcep8wkafoSHFk4SJBE9FSit52wNQZl2fi3VGy1+dYFlnNXZCRHBbh3UCQQCCJVXNCyX5hEKgYzYzoBAPqIVN45PACF2G0bwea0gyGWny+W0ia+QAj4uwDibSsxUyZExBfyKX+MPDegS9dHBo";

    /**
     * 用户登录成功后生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     *
     * @param ttlMillis
     * @param id
     * @param userName
     * @param passWord
     * @return
     */
    public static String createJWT(long ttlMillis, String id, String userName, String passWord) {
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap(50);
        claims.put("id", id);
        claims.put("username", userName);
        claims.put("password", passWord);
        /*
            生成签名的时候使用的秘钥 secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
           一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
         */
        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(new Date(nowMillis))
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(userName)
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(SignatureAlgorithm.HS256, generalKey());
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //设置过期时间
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 获取token串
     *
     * @param auth token
     * @return String
     */
    public static String getToken(String auth) {
        if ((auth != null) && (auth.length() > AUTH_LENGTH)) {
            String headStr = auth.substring(0, 6).toLowerCase();
            if (headStr.compareTo(BEARER) == 0) {
                auth = auth.substring(7);
            }
            return auth;
        }
        return null;
    }

    /**
     * 解析jsonWebToken
     *
     * @param jsonWebToken token串
     * @return Claims
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {

            JwtParser jwtParser = Jwts.parser().setSigningKey(generalKey());
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(jsonWebToken);
            Claims claims = claimsJws.getBody();
            return claims;
        } catch (IllegalArgumentException ie) {
            log.error(ie.getMessage());
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            return null;
        }
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64Utils.decode(KEY.getBytes());
        SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}
