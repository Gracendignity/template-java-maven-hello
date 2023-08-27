package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserInfo implements MyAction {
    private static final String DB_Manager = "jdbc:sqlite:manager.db";
    private static final String ACTION_NAME = "info";
    private Scanner scanner = null;

    public UserInfo(Scanner scanner) {
        this.scanner = scanner;
    }
    
    @Override
    public String getActionName() {
        return UserInfo.ACTION_NAME;
    }

    @Override
    public void run(String[] args) {
         
        System.out.println("欢迎进入个人信息完善界面!");
        System.out.println("请输入你的用户名:");
        String username = this.scanner.nextLine();

        System.out.println("请输入你的注册账户的日期:");
        String egistrationTime = this.scanner.nextLine();

        System.out.println("请输入你的电话号码:");
        String phoneNumber = this.scanner.nextLine();

        
        System.out.println("请输入你注册时的邮箱:");
        String userMail = this.scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_Manager);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO manager (username, registrationTime, phoneNumber,userEmail) VALUES (?,?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, egistrationTime);
            statement.setString(3, phoneNumber);
            statement.setString(4, userMail);
            statement.executeUpdate();
            System.out.println("写入数据库成功!");
        } catch (SQLException e) {
            System.out.println("写入数据库失败: " + e.getMessage());
        }

    }
}
