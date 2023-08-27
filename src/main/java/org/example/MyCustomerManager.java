package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MyCustomerManager implements MyAction {
    private static final String DB_Manager = "jdbc:sqlite:manager.db";
    private static final String ACTION_NAME = "customer";
    private Scanner scanner = null;

    public MyCustomerManager(Scanner scanner) {
        this.scanner = scanner;
    }
    
    @Override
    public String getActionName() {
        return MyCustomerManager.ACTION_NAME;
    }

    @Override
    public void run(String[] args) {
        System.out.println("欢迎进入客户管理界面!");

        System.out.println("列出客户所有信息：list,删除客户信息:delete,查询客户信息:check");
        String Input = this.scanner.nextLine();
        switch(Input){
            case "list": 
                         customerList();
                         break;
            case "delete":
                          deleteInfo();
                          break;
            case "check":
                         checkInfo();
        }
    }
    public void customerList(){
        try (Connection connection = DriverManager.getConnection(DB_Manager);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM manager")) {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String userLevel = resultSet.getString("userLevel");
    
        double totalAmount = resultSet.getDouble("totalAmount");
        String phoneNumber = resultSet.getString("phoneNumber");
        String userMail = resultSet.getString("userMail");
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("UserLevel: " + userLevel);
        System.out.println("totalAmount: " + totalAmount);
        System.out.println("phoneNumber: " + phoneNumber);
        System.out.println("User Mail: " + userMail);
        System.out.println("------------------------");
       }
      } catch (SQLException e) {
    System.out.println("Failed to retrieve data from the table: " + e.getMessage());
     }
    }

    public void deleteInfo(){
        try (Connection connection = DriverManager.getConnection(DB_Manager);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM manager WHERE id = ?")) {
            // 从用户输入获取要删除的行的唯一标识
            System.out.print("输入要删除客户的用户名:");
            String username = this.scanner.nextLine();

            // 确认是否继续删除
            System.out.print("Are you sure you want to delete row with ID " + username + "? (Y/N): ");
            String confirmation = this.scanner.next();
            if (confirmation.equalsIgnoreCase("Y")) {
                statement.setString(0, confirmation);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Row deleted successfully!");
                } else {
                    System.out.println("No rows deleted.");
                }
            } else {
                System.out.println("Deletion canceled.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to delete row: " + e.getMessage());
        }
    }

    /**
     * 
     */
    public void checkInfo(){
        try (Connection connection = DriverManager.getConnection(DB_Manager);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM manager WHERE id = ? OR username = ?")) {
            // 从用户输入获取查询条件
            System.out.print("Enter the ID: ");
            int id = this.scanner.nextInt();

            System.out.print("Enter the username: ");
            String username = this.scanner.nextLine();

            statement.setInt(1, id);
            statement.setString(2, username);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String userLevel = resultSet.getString("userLevel");
                String  registrationTime = resultSet.getString(" registrationTime");
                double totalAmount = resultSet.getDouble("totalAmount");
                String phoneNumber = resultSet.getString("phoneNumber");
                String userMail = resultSet.getString("userMail");
                System.out.println("ID: " + id);
                System.out.println("Username: " + username);
                System.out.println("UserLevel: " + userLevel);
                System.out.println("RegistrationTime: " + registrationTime);
                System.out.println("totalAmount: " + totalAmount);
                System.out.println("phoneNumber: " + phoneNumber);
                System.out.println("User Mail: " + userMail);
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Failed to query data from the table: " + e.getMessage());
        }
    }
}
