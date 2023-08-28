package org.example;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    String userInput = "";

    @Override
    public void run(String[] args) {
        System.out.println("欢迎进入注册界面!");
        List<MyAction> list = new ArrayList<MyAction>();

        UserInfo info = new UserInfo(scanner);
        list.add(info);
        while(true) {
            System.out.print("请输入用户名:");
            String username = this.scanner.nextLine();

            if(username.length()<5){
               System.out.println("用户名长度不少于5个字符,请重新输入:");
               username = this.scanner.nextLine();
            }
            
           try(Connection connection = DriverManager.getConnection(DB_URL);
           PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                
                 
                if (resultSet.next()) {
                    String name = resultSet.getString("username");
                    if (username.equals(name)) {
                        System.out.println("用户名已存在，请重新输入:");
                         username = this.scanner.nextLine();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.print("请输入密码:");
            String password = "";
            password = this.scanner.nextLine();
            
            while(!validatePassword(password))
            {
                System.out.println("注意!密码必须包含大小写字母、数字、标点符号且password大于8位!");
                System.out.print("请重新输入密码:");
                password = this.scanner.nextLine();
               break;
            }
            password=MyPasswordSecurity.PasswordEncryption(password);
            System.out.print("请输入你的邮箱:");
            String userMail = this.scanner.nextLine();


            boolean success = this.userManager.registerUser(username, password,userMail);
            if(success){
                System.out.println("注册成功! 请完善个人信息:Info");
                System.out.println("请完善个人信息：info");
                userInput = this.scanner.nextLine();

                    String actionName = null;
                    for(MyAction twoAction: list) {
                        actionName = twoAction.getActionName();
        
                        if (userInput.equalsIgnoreCase(actionName)) {
                            twoAction.run(null); 
                        }
                   }
                   System.out.println("你的个人信息已完善!"); 
                    break; 
                }
        }
    } 
     public boolean validatePassword(String password) {
    // 判断密码长度大于8个字符
    if (password.length() <= 8) {
        return false;
    }

    // 密码正则表达式：包含大小写字母、数字和标点符号，且长度大于8
    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{9,}$";

    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(password);

    return matcher.matches();
    }
}