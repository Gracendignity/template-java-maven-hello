package org.example;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;

public class MyPasswordManager implements MyAction {

    private static final String DB_URL = "jdbc:sqlite:users.db";

    Scanner input = new Scanner(System.in);

    private static final String ACTION_NAME = "passWord";

    private Scanner scanner = null;

    public MyPasswordManager(Scanner scanner) {
        this.scanner = scanner;
    }
    
    @Override
    public String getActionName() {
        return MyPasswordManager.ACTION_NAME;
    }

    @Override
    public void run(String[] args) {
        System.out.println("欢迎进入密码管理界面!");
        System.out.println("修改密码：change,重置密码:reset");
        String Input = input.nextLine();
        switch(Input){
            case "change": 
                         changePassword();
                         break;
            case "reset":
                          resetPassword();
                          break;

        }
    }
    public void changePassword(){
        while(true) {
            
            System.out.print("请输入用户名:");
            String username = this.scanner.nextLine();

            System.out.print("请输入旧密码:");
            String password = this.scanner.nextLine();

            System.out.print("请输入新密码:");
            String newPassword = this.scanner.nextLine();
            
            try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement statement = connection.prepareStatement("UPDATE Users SET password = ? WHERE username = ?")) {
           statement.setString(1, newPassword);
           statement.setString(2, username);
           int rowsUpdated = statement.executeUpdate();
           if (rowsUpdated > 0) {
               System.out.println("User information updated successfully!");
           } else {
               System.out.println("User not found!");
           }
       } catch(SQLException e) {
           System.out.println("Error updating user information: " + e.getMessage());
       }
       System.out.println("修改密码成功!");
       break;
        }
    }
    public void resetPassword(){
        while(true) {

            System.out.print("请输入用户名:");
            String username = this.scanner.nextLine();

            try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Users WHERE username = ?")) {
            statement.setString(1, username);
            int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("User deleted successfully!");

            System.out.print("请输入用户名:");
            username = this.scanner.nextLine();

            System.out.print("请输入重置后的密码:");
            String password = this.scanner.nextLine();

        } else {
            System.out.println("User not found!");
        }
    } catch(SQLException e) {
        System.out.println("Error deleting user: " + e.getMessage());
    }
      System.out.println("重置密码成功!");
      break;
        } 
    }
    
}