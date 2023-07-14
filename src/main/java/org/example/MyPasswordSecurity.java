package org.example;

import java.io.Console;
import java.util.Arrays;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyPasswordSecurity {
      // 用于加密密码的哈希算法（这里使用SHA-256）
      private static final String HASH_ALGORITHM = "SHA-256";

      // 生成密码的哈希值
      public static String generateHashedPassword(String password) {
          try {
              // 创建哈希算法对象
              MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
              
              // 将密码转换为字节数组
              byte[] hashedBytes = digest.digest(password.getBytes());
              
              // 将字节数组转换为十六进制字符串
              StringBuilder hexString = new StringBuilder();
              for (byte b : hashedBytes) {
                  String hex = String.format("%02x", b);
                  hexString.append(hex);
              }
              
              return hexString.toString();
          } catch (NoSuchAlgorithmException e) {
              e.printStackTrace();
          }
          
          return null;
      }
      public static void hidePassword(String password){
        // 获取控制台对象
        Console console = System.console();
        
        if (console == null) {
            System.out.println("No console available. Please run the program from the command line.");
            System.exit(1);
        }
        
        // 读取密码
        char[] passwordArray = console.readPassword("Enter your password: ");
        
        // 将密码转换为字符串进行处理
        password = new String(passwordArray);
        
        // 清除密码的字符数组
        Arrays.fill(passwordArray, '*');
        System.out.print(passwordArray);
      }
    
    
}
