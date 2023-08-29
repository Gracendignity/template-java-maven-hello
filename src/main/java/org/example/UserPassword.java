package org.example;
import java.util.Random;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserPassword implements MyAction {

    private static final String DB_URL = "jdbc:sqlite:users.db";
     
    private static final String ACTION_NAME = "passWord";

    private Scanner scanner = null;

    public UserPassword(Scanner scanner) {
        this.scanner = scanner;
    }
    
    @Override
    public String getActionName() {
        return UserPassword.ACTION_NAME;
    }

    @Override
    public void run(String[] args) {
            
        System.out.println("欢迎进入密码管理界面!");

        System.out.println("修改密码：change,重置密码:reset");
        String Input = this.scanner.nextLine();
        switch(Input){
            case "change": 
                         changePassword();
                         break;
            case "reset":
                          Resetpassword();
                          break;

        }
    }
    public void Resetpassword(){
        while(true){
           System.out.println("请输入用户名：");
            String username = this.scanner.nextLine();
            
            System.out.println("请输入注册时使用的邮箱地址：");
            String userMail = this.scanner.nextLine();
            
            if (resetPassword(username, userMail)) {
                System.out.println("重置密码邮件已发送，请检查您的邮箱。");
                break;
                }
            else {
                System.out.println("用户名或邮箱地址不正确，请重新输入:");
                System.out.println("请重新输入用户名：");
                username = this.scanner.nextLine();
                System.out.println("请重新输入注册时使用的邮箱地址：");
                userMail = this.scanner.nextLine();
                if (resetPassword(username, userMail)) {
                    System.out.println("重置密码邮件已发送，请检查您的邮箱。请返回登录界面重新登录");
                    String Input = this.scanner.nextLine();
                    if (Input.equals("q")) {
                    }
            }
            break;
        }
    }
}
    

    /**
     * @param username
     * @param userMail
     * @return
     */
    public boolean resetPassword(String username, String userMail) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                String storedEmail = resultSet.getString("userMail");
                if (storedEmail != null && userMail.equals(storedEmail)) {
                    // 生成随机密码
                    String newPassword = generateRandomPassword();
    
                    // 将新密码发给用户
                    System.out.println("您的新密码是：" + newPassword);
                    newPassword = MyPasswordSecurity.PasswordEncryption(newPassword);
                    
                    // 保存新密码到数据库中
                    try (PreparedStatement updateStatement = connection.prepareStatement("UPDATE Users SET password = ? WHERE username = ?")) {
                        updateStatement.setString(1, newPassword);
                        updateStatement.setString(2, username);
                        
                        int rowsUpdated = updateStatement.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("密码更新成功！");
                            return true;
                        } else {
                            System.out.println("密码更新失败！");
                        }
                    } catch (SQLException e) {
                        // 抛出异常，由外部捕获并处理
                        throw new SQLException("更新用户信息出错: " + e.getMessage(), e);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("重置密码失败: " + e.getMessage());
        }
        return false;
    }
    public static String generateRandomPassword() {

            // 生成一个随机的9位密码
            String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuilder password = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < 9; i++) {
                int index = random.nextInt(chars.length());
                password.append(chars.charAt(index));
            }
            return password.toString();
    }

    public void changePassword(){
        while(true) {
                
                System.out.print("请输入用户名:");
                String username = this.scanner.nextLine();
    
                System.out.print("请输入旧密码:");
                String password = this.scanner.nextLine();
    
                System.out.print("请输入新密码:");
                String newPassword = this.scanner.nextLine();
                newPassword = MyPasswordSecurity.PasswordEncryption(newPassword);
                try (Connection connection = DriverManager.getConnection(DB_URL);
                PreparedStatement statement = connection.prepareStatement("UPDATE Users SET password = ? WHERE username = ?")) {
                statement.setString(1, newPassword);
                statement.setString(2, username);
                int rowsUpdated = statement.executeUpdate();
               if (rowsUpdated > 0) {
                   System.out.println("User information updated successfully!");
                   
                   System.out.println("修改密码成功,请退出重新登录");
                   break;
                }
                   else {
                   System.out.println("User not found!");
                   }  
            }
            catch(SQLException e) {
               System.out.println("Error updating user information: " + e.getMessage());
           }
        }
    }
}
