package com.example.shirodemo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @Author: freaxjj
 * @Desc:
 * @Date: 4/9/21 3:24 PM
 */
@Slf4j
public class JwtUtil {

    private static final Long EXPIRE_TIME = 5 * 60 * 1000L;

    private static final String SECRET = "SHIRO+JWT";

    /**
     * 生成 token 时，指定 token 过期时间 EXPIRE_TIME 和签名密钥 SECRET，
     * 然后将 expireDate 和 username 写入 token 中，并使用带有密钥的 HS256 签名算法进行签名
     * @param username
     * @return
     */
    public static String createToken(String username) {
        String token = null;
        try {
            // 过期时间
            Date expireDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            // 加密算法
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            token = JWT.create()
                    .withClaim("username", username)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            log.error("Failed to create token. {}", e.getMessage());
        }
        return token;
    }

    /**
     * 验证token，如果验证失败，便会抛出异常
     * @param token
     * @param username
     * @return
     */
    public static boolean verify(String token, String username) {
        boolean isSuccess = false;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            // 验证token
            verifier.verify(token);
            isSuccess = true;
        } catch (UnsupportedEncodingException e) {
            log.error("Token is invalid. {}", e.getMessage());
        }
        return isSuccess;
    }

    /**
     * 在 createToken()方法里，有将 username 写入 token 中。现在可从 token 里获取 username
     * @param token
     * @return
     */
    public static String getUsernameFromToken(String token) {
        try {
            DecodedJWT decode = JWT.decode(token);
            String username = decode.getClaim("username").asString();
            return username;
        } catch (JWTDecodeException e) {
            log.error("Failed to Decode jwt. {}", e.getMessage());
            return null;
        }
    }
}
