package org.example;

import java.io.Console;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

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
    public static void hidePassword(String password){

        try (Scanner sc = new Scanner(System.in)) {
            // 获取控制台对象
            Console console = System.console();
            
            if (console == null) {
                System.out.println("No console available. Please run the program from the command line.");
                System.exit(1);
            }
            
            // 读取密码
            char[] passwordArray = console.readPassword("");
            System.out.println("是否要显示密码:Yes or No");
            String Input = sc.nextLine();

            if(Input.equals("Yes")){
                System.out.println(new String(passwordArray)); 
            }
            
      else{

            // 打印星号代替密码
            Arrays.fill(passwordArray, '*');
            System.out.println(new String(passwordArray));
      }

            // 清除密码的字符数组
            Arrays.fill(passwordArray, ' ');
        }
    }
}
