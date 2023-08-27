package org.example;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MyProductManager implements MyAction{
    private static final String DB_Product = "jdbc:sqlite:Product.db";
    private static final String ACTION_NAME = "product";
    private Scanner scanner;

    public MyProductManager(Scanner scanner) {
        this.scanner = scanner;
    }
    
    @Override
    public String getActionName() {
        return MyProductManager.ACTION_NAME;
    }

    @Override
    public void run(String[] args) {
        System.out.println("欢迎进入商品管理界面!");

        System.out.println("列出所有商品信息：list,添加商品信息:add,删除商品信息:delete,修改商品信息:change,查询商品信息:check");
        String Input = this.scanner.nextLine();
        switch(Input){
            case "list": 
                         productList();
                         break;
           /* case "add":
                        addProduct();
            case "delete":
                          deleteInfo();
                          break;
            case "change":
                          changeProduct();
                          break;
            case "check":
                         checkInfo();*/ 
        }
    }

    public void  productList(){
        try (Connection connection = DriverManager.getConnection(DB_Product);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Product")) {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
        String id = resultSet.getString("productID");
        String productName = resultSet.getString("productName");
        String manufacturer = resultSet.getString("manufacturer");
    
        String model = resultSet.getString("model");
        Double purchasePricer = resultSet.getDouble("purchasePrice");
        Double retailPrice = resultSet.getDouble("retailPrice");
        String quantity = resultSet.getString("quantity");
        System.out.println("ID: " + id);
        System.out.println("productName: " + productName);
        System.out.println("manufacturer: " + manufacturer);
       
        System.out.println("model: " + model);
        System.out.println("purchasePricer: " + purchasePricer);
        System.out.println("retailPrice: " + retailPrice);
        System.out.println("quantity: " + quantity);
        System.out.println("------------------------");
       }
      } catch (SQLException e) {
    System.out.println("Failed to retrieve data from the table: " + e.getMessage());
     }
    }
    
}
