package org.example;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MyPasswordSecurity {
      // 生成密码的哈希值
    public static String PasswordEncryption(String password) {
        try {
            // 创建MessageDigest对象，使用MD5算法进行加密
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // 将密码字符串转换为字节数组进行加密处理
            byte[] hashBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            
            // 将字节数组转换为BigInteger对象
            BigInteger hashNum = new BigInteger(1, hashBytes);
            
            // 将BigInteger对象转换为16进制的字符串表示
            String encryptedPassword = hashNum.toString(16);
            
            // 在前导零缺失时进行补全
            while (encryptedPassword.length() < 32) {
                encryptedPassword = "0" + encryptedPassword;
            }
            
            return  encryptedPassword;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: MD5 algorithm not available.");
        }
        return null;
    }
}
