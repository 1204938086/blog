package com.wyj.blog.utils;

import io.jsonwebtoken.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;


public class TokenUtils {
    /**
     * 由字符串生成加密key,私钥
     *
     * @return      秘钥
     */
    public static SecretKey generalKey(String stringKey) {
        // 本地的密码解码
        byte[] encodedKey = stringKey.getBytes();
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 创建Token
     * @param username      用户名
     * @param password      密码
     * @param ttlMinutes    过期秒数
     * @return              Token
     */
    public static String CreateToken(String username,String password,Long ttlMinutes){
        if (ttlMinutes == null){
            ttlMinutes = 1000L * 60 * 60 * 24; // 默认一天过期
        }else {
            ttlMinutes = ttlMinutes * 1000 * 60; // 将分钟转换为毫秒
        }
        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);


        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 构建秘钥
        SecretKey secretKey = generalKey(password);

        // 创建Token
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)                            // 签发时间
                .setExpiration(new Date(nowMillis + ttlMinutes))    // 设置过期时间
                .signWith(signatureAlgorithm, secretKey);           // 签名算法以及密匙
        return builder.compact();
    }

    /**
     * 验证Token是否有效
     * @param token     Token文本
     * @param password  密码，用于生成秘钥
     * @return          是否有效
     */
    public static Integer VerifyToken(String token,String password){
        SecretKey key = generalKey(password);  //签名秘钥，和生成的签名的秘钥一模一样
        try {
            Jwts.parser()                                       //得到DefaultJwtParser
                    .setSigningKey(key)                         //设置签名的秘钥
                    .parseClaimsJws(token).getSignature();     //设置需要解析的jwt
            return 1; // 解析成功,Token正常
        }catch(MalformedJwtException malformedJwtException){
            return 0; // Token过期
        }catch (Exception Exception) {
            return -1; // 解析失败,Token有问题
        }
    }
}
