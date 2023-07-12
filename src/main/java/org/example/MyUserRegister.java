package org.example;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;

public class MyUserRegister implements MyAction {

    private static final String DB_URL = "jdbc:sqlite:users.db";

    private static final String ACTION_NAME = "register";
    private Scanner scanner = null;
    private MyUserManager userManager = null; 

    public MyUserRegister(Scanner scanner,MyUserManager userManager) {
        this.scanner = scanner;
        this.userManager = userManager;
    }
    
    @Override
    public String getActionName() {
        return MyUserRegister.ACTION_NAME;
    }

    @Override
    public void run(String[] args) {
        System.out.println("欢迎进入注册界面!");

        while(true) {
            System.out.print("请输入用户名:");
            String username = this.scanner.nextLine();
            
           try(Connection connection = DriverManager.getConnection(DB_URL);
           PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                
                 
                if (resultSet.next()) {
                    String name = resultSet.getString("username");
                    if (username.equals(name)) {
                        System.out.println("用户名已存在，请重新输入");
                         username = this.scanner.nextLine();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.print("请输入密码:");
            String password = this.scanner.nextLine();

            boolean success = this.userManager.registerUser(username, password);
            if(success){
                System.out.println("注册成功,返回上一级");
                break;
            }
        }
    } 
}