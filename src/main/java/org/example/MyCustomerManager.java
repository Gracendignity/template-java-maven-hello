package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

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
        List<MyAction> list = new ArrayList<MyAction>();


        UserPassword passWord = new UserPassword(scanner);
        list.add(passWord);
        
        UserInfo info = new UserInfo(scanner);
        list.add(info);

        MyCustomerManager customer = new MyCustomerManager(scanner);
        list.add(customer);
        
        MyProductManager product = new MyProductManager(scanner);
        list.add(product);

        String userInput ="";
        while(true){
         
        System.out.println("列出客户所有信息：List,删除客户信息:delete,查询客户信息:check");
        String Input = this.scanner.nextLine();
        switch(Input){
            case "List": 
                         customerList();
                         break;
            case "delete":
                          deleteInfo();
                          break;
            case "check":
                         checkInfo();
                         break;
        }
        System.out.println("是否要返回上一级：yes/no:");
        userInput = this.scanner.nextLine();
        if (userInput.equals("yes")) {
                    System.out.println("请输入你的指令:密码管理:passWord,顾客管理:customer,商品管理:product,q 退出");
                    userInput = this.scanner.nextLine();

                    
                    if (userInput.equals("q")) {
                        break; 
                    }
                    
                    String actionName = null;
                    for(MyAction twoAction: list) {
                        actionName = twoAction.getActionName();
        
                        if (userInput.equalsIgnoreCase(actionName)) {
                            twoAction.run(null); 
                        }
                   }
            
             break;
            }
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
        String userEMail = resultSet.getString("userEMail");
        System.out.println("ID: " + id);
        System.out.println("Username: " + username);
        System.out.println("UserLevel: " + userLevel);
        System.out.println("totalAmount: " + totalAmount);
        System.out.println("phoneNumber: " + phoneNumber);
        System.out.println("User Mail: " + userEMail);
        System.out.println("------------------------");
        }
      } catch (SQLException e) {
    System.out.println("Failed to retrieve data from the table: " + e.getMessage());
     }
    }

    public void deleteInfo(){
        try (Connection connection = DriverManager.getConnection(DB_Manager);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM manager WHERE username = ?")) {
            // 从用户输入获取要删除的行的唯一标识
            System.out.print("输入要删除客户的用户名:");
            String username = this.scanner.nextLine();

            // 确认是否继续删除
            System.out.print("Are you sure you want to delete row with username " + username + "? (Y/N): ");
            String confirmation = this.scanner.nextLine();
            if (confirmation.equalsIgnoreCase("Y")) {
                statement.setString(1, username);
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
            System.out.println("请选择查询客户信息的方式:通过id查询:ID,通过用户名查询:name:");
            String input = this.scanner.nextLine();
            if(input.equals("ID")){
                System.out.print("输入要查询的id: ");
                int id = Integer.parseInt(this.scanner.nextLine());
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String userLevel = resultSet.getString("userLevel");
                    String username = resultSet.getString("username");
                    double totalAmount = resultSet.getDouble("totalAmount");
                    String phoneNumber = resultSet.getString("phoneNumber");
                    String userEMail = resultSet.getString("userEMail");
                    System.out.println("查询结果如下:");
                    System.out.println("ID: " + id);
                    System.out.println("Username: " + username);
                    System.out.println("UserLevel: " + userLevel);
                    System.out.println("totalAmount: " + totalAmount);
                    System.out.println("phoneNumber: " + phoneNumber);
                    System.out.println("User Mail: " + userEMail);
                    System.out.println("------------------------");
                }
            }
            else if(input.equals("name")){
    
            System.out.print("输入要查询的用户名: ");
            String username = this.scanner.nextLine();

            statement.setString(1, username);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String userLevel = resultSet.getString("userLevel");
                double totalAmount = resultSet.getDouble("totalAmount");
                String phoneNumber = resultSet.getString("phoneNumber");
                String userEMail = resultSet.getString("userEMail");
                System.out.println("查询结果如下:");
                System.out.println("ID: " + id);
                System.out.println("Username: " + username);
                System.out.println("UserLevel: " + userLevel);
                System.out.println("totalAmount: " + totalAmount);
                System.out.println("phoneNumber: " + phoneNumber);
                System.out.println("User Mail: " + userEMail);
                System.out.println("------------------------");
            }
            }
        } catch (SQLException e) {
            System.out.println("Failed to query data from the table: " + e.getMessage());
        }
    }
}
