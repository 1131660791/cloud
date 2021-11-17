package com.util.jwt;

import com.util.constant.auth.TokenConstant;
import com.util.encryption.rsa.RSAUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * JwtUtil
 * <p>
 * 一个JWT实际上就是一个字符串，它由三部分组成：头部、载荷(Payload)与签名。
 * 组成 ：
 * 1：头部：头部用于描述关于该JWT的最基本的信息，例如其类型以及签名所用的算法等。这也可以被表示成一个JSON对象。
 * 2：载荷：
 * JWT主体内容主要包含以下三种类型：
 * (1) Reserved（保留声明），它的含义就像是编程语言的保留字一样，属于JWT规范中规定好的，这类声明不是必须的，但是是建议使用的。有以下几种：
 * (2) iss: JWT的签发者
 * (3) sub: 该JWT所面向的用户
 * (4) aud:JWT的接收方
 * (5) exp(expires): 过期时间，这里是一个Unix时间戳
 * (6) iat(issued at): 签发时间，这里是一个Unix时间戳
 * 3：将上面的两个编码后的字符串都用句号 .连接在一起（头部在前），最后，我们将上面拼接完的字符串用HS256算法进行加密。在加密的时候，我们还需要提供一个密钥（secret）。这就是签名。
 * 最后将这一部分签名也拼接在被签名的字符串后面，我们就得到了完整的JWT
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
     * 用户登录成功后生成Jwt
     * 使用Hs256算法  私匙使用用户密码
     *
     * @param ttlMillis - 过期时间 单位 分钟
     * @param id
     * @param userName
     * @param passWord
     * @return
     */
    public static String createJWT(long ttlMillis, String id, String userName, String passWord) {
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap(50);
        claims.put("id", id);
        claims.put("username", userName);
        claims.put("password", passWord);
        /*
           生成签名的时候使用的秘钥 secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
           一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
         */
        // 下面就是在为payload添加各种标准声明和私有声明了
        // 这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token，从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                .setIssuer("hzw")
                // iat: jwt的签发时间
                .setIssuedAt(new Date(nowMillis))
                // 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(userName);

        if (ttlMillis >= 0) {
            //设置过期时间 单位 毫秒
            builder.setExpiration(new Date(nowMillis + (ttlMillis * (1000 * 60))));
        }
        // 设置签名使用的签名算法和签名使用的秘钥
        builder.signWith(SignatureAlgorithm.RS256, RSAUtil.getPrivateKey());
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
            JwtParser jwtParser = Jwts.parser().setSigningKey(RSAUtil.getPrivateKey());
            // 解析token
            Claims claims = jwtParser.parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (IllegalArgumentException ie) {
            log.warn("IllegalArgumentException 错误：" + ie);
            return null;
        } catch (SignatureException e) {
            log.warn("SignatureException 错误：" + e);
            return null;
        } catch (UnsupportedJwtException uje) {
            log.warn("UnsupportedJwtException 错误 密钥不正确：" + uje);
            return null;
        } catch (Exception e) {
            log.error("Exception 错误：" + e);
            return null;
        }
    }

    /**
     * 由字符串生成加密key
     * --
     * 对称加密，如AES
     * 基本原理：将明文分成N个组，然后使用密钥对各个组进行加密，形成各自的密文，最后把所有的分组密文进行合并，形成最终的密文。
     * 优势：算法公开、计算量小、加密速度快、加密效率高
     * 缺陷：双方都使用同样密钥，安全性得不到保证
     * --
     * 非对称加密，如RSA
     * 基本原理：同时生成两把密钥：私钥和公钥，私钥隐秘保存，公钥可以下发给信任客户端
     * 私钥加密，持有私钥或公钥才可以解密
     * 公钥加密，持有私钥才可解密
     * 优点：安全，难以破解
     * 缺点：算法比较耗时
     * --
     * 不可逆加密，如MD5，SHA
     * 基本原理：加密过程中不需要使用密钥，输入明文后由系统直接经过加密算法处理成密文，这种加密后的数据是无法被解密的，无法根据密文推算出明文。
     *
     * @return
     */
    public static SecretKey generalKey(BigInteger keys) {
        byte[] encodedKey = keys.toByteArray();
        SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "RSA");
        return key;
    }
}
