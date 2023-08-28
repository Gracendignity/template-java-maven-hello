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
            case "add":
                        addProduct();
            case "delete":
                          deleteInfo();
                          break;
            case "change":
                          changeProduct();
                          break;
            case "check":
                         checkInfo();
        }
    }

    public void  productList(){
        try (Connection connection = DriverManager.getConnection(DB_Product);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Product")) {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
        String productID = resultSet.getString("productID");
        String productName = resultSet.getString("productName");
        String manufacturer = resultSet.getString("manufacturer");
    
        String model = resultSet.getString("model");
        Double purchasePricer = resultSet.getDouble("purchasePrice");
        Double retailPrice = resultSet.getDouble("retailPrice");
        int quantity = resultSet.getInt("quantity");
        System.out.println("ID: " + productID);
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

    public void addProduct(){
        System.out.print("请输入商品的编号：");
        String productID = this.scanner.nextLine();

        System.out.print("请输入商品的名称：");
        String productName = this.scanner.nextLine();

        System.out.print("请输入商品的生产厂家：");
        String manufacturer = this.scanner.nextLine();

        System.out.print("请输入商品的类型：");
        String model = this.scanner.nextLine();

        System.out.print("请输入商品的进价：");
        double purchasePrice = this.scanner.nextDouble();

        System.out.print("请输入商品的售价：");
        double retailPrice = this.scanner.nextDouble();

        System.out.print("请输入商品的进货数量：");
        int quantity = this.scanner.nextInt();

        try (Connection connection = DriverManager.getConnection(DB_Product);
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Product (productID, productName, manufacturer, model, purchasePrice, retailPrice, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
       // 设置要添加的信息
        statement.setString(1, productID); // 设置ID
        statement.setString(2, productName); // 设置名称
        statement.setString(3, manufacturer);
        statement.setString(4,  model);
        statement.setDouble(5,  purchasePrice);
        statement.setDouble(6, retailPrice);
        statement.setInt(7, quantity);
      // 执行插入操作
      int rowsAffected = statement.executeUpdate();
      System.out.println("已添加 " + rowsAffected + " 条信息");
     } catch (SQLException e) {
    e.printStackTrace();
      }
    }

    public void deleteInfo(){
        try (Connection connection = DriverManager.getConnection(DB_Product);
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Product WHERE productName = ?")) {
       // 根据商品名称删除商品信息
       System.out.print("输入要删除的商品名称:");
       String productName = this.scanner.nextLine();

       // 确认是否继续删除
       System.out.print("Are you sure you want to delete row with productName " + productName + "? (Y/N): ");
       String confirmation = this.scanner.nextLine();
       if (confirmation.equalsIgnoreCase("Y")) {
           statement.setString(1, productName);
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

    public void changeProduct(){
        String  productName="";
        String  manufacturer="";
        String  model="";
        double purchasePrice=0;
        double retailPrice=0;
        int quantity=0;
        System.out.print("请输入需修改的商品编号：");
        String productID = this.scanner.nextLine();
        System.out.print("请输入需修改的商品信息：商品名称：productName，生产厂家：manufacturer，商品类型：model，商品进价：purchasePrice，商品售价：retailPrice，商品进货量：quantity：");
        String  info = this.scanner.nextLine();
        switch(info){
            case "productName":
                System.out.print("请输入新的商品名称：");
                productName = this.scanner.nextLine();
                break;
            case "manufacturer":
                System.out.print("请输入新的生产厂家：");
                manufacturer = this.scanner.nextLine();
                 break;
            case "model":
                 System.out.print("请输入新的商品类型：");
                 model = this.scanner.nextLine();
                  break;
            case "purchasePrice":
                  System.out.print("请输入新的商品进价：");
                  purchasePrice = this.scanner.nextDouble();
                  break;
            case "retailPrice":
                  System.out.print("请输入新的商品售价：");
                  retailPrice = this.scanner.nextDouble();
                  break;
            case "quantity":
                  System.out.print("请输入新的商品进货量：");
                  quantity = this.scanner.nextInt();
                  break;
        }

        try (Connection connection = DriverManager.getConnection(DB_Product);
        PreparedStatement statement = connection.prepareStatement("UPDATE Product SET productName = ?, SET manufacturer = ?, SET model = ?, SET purchasePrice = ?, SET retailPrice = ?, SET quantity = ? WHERE productID = ?")) {
       // 设置要修改的信息
        statement.setString(1, productName);
        statement.setString(2, manufacturer);
        statement.setString(3, model);
        statement.setDouble(4, purchasePrice); 
        statement.setDouble(5, retailPrice); 
        statement.setInt(6, quantity);
        statement.setString(7,productID); // 根据productID指定要修改的记录

        // 执行更新操作
       int rowsAffected = statement.executeUpdate();
        System.out.println("已修改 " + rowsAffected + " 条信息");
        } catch (SQLException e) {
         e.printStackTrace();
       }
    }

    public void checkInfo(){
        try (Connection connection = DriverManager.getConnection(DB_Product);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Product WHERE productName = ? OR manufacturer = ? OR retailPrice = ?")) {
            // 从用户输入获取查询条件
            System.out.println("请选择查询商品信息的方式:通过商品名称查询:name，根据生产厂家:N,根据售价：P");
            String input = this.scanner.nextLine();
            if(input.equals("name")){
                System.out.print("输入要查询的商品名称: ");
                String productName = this.scanner.nextLine();
                statement.setString(1, productName);
                ResultSet resultSet = statement.executeQuery();
                if (!resultSet.next()) {
                    System.out.println("该商品不存在！");
                } else {
                    do {
                        int productID = resultSet.getInt("productID");
                        String manufacturer = resultSet.getString("manufacturer");
                        String model = resultSet.getString("model");
                        double purchasePrice = resultSet.getDouble("purchasePrice");
                        double retailPrice = resultSet.getDouble("retailPrice");
                        int quantity = resultSet.getInt("quantity");

                        System.out.println("查询结果如下:");
                        System.out.println("ID: " + productID);
                        System.out.println("商品名称: " + productName);
                        System.out.println("制造商: " + manufacturer);
                        System.out.println("型号: " + model);
                        System.out.println("进货价格: " + purchasePrice);
                        System.out.println("零售价格: " + retailPrice);
                        System.out.println("数量: " + quantity);
                        System.out.println("------------------------");
                    } while (resultSet.next());
                }
            }
            else if(input.equals("N")){
    
            System.out.print("输入要查询的生产厂家: ");
            String manufacturer = this.scanner.nextLine();

            statement.setString(2, manufacturer);
            
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("该商品不存在！");
            } else {
            do {
                int productID = resultSet.getInt("productID");
                String productName = resultSet.getString("productName");
                String model = resultSet.getString("model");
                double purchasePrice = resultSet.getDouble("purchasePrice");
                double retailPrice = resultSet.getDouble("retailPrice");
                int quantity = resultSet.getInt("quantity");

                System.out.println("查询结果如下:");
                System.out.println("ID: " + productID);
                System.out.println("商品名称: " + productName);
                System.out.println("制造商: " + manufacturer);
                System.out.println("型号: " + model);
                System.out.println("进货价格: " + purchasePrice);
                System.out.println("零售价格: " + retailPrice);
                System.out.println("数量: " + quantity);
                System.out.println("------------------------");
            }while (resultSet.next());
            }
        }
        else if(input.equals("P")){
    
            System.out.print("输入要查询的售价: ");
            double retailPrice = this.scanner.nextDouble();

            statement.setDouble(3, retailPrice);
            
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                System.out.println("该商品不存在！");
            } else {
            do {
                int productID = resultSet.getInt("productID");
                String productName = resultSet.getString("productName");
                String model = resultSet.getString("model");
                double purchasePrice = resultSet.getDouble("purchasePrice");
                String manufacturer = resultSet.getString("manufacturer");
                int quantity = resultSet.getInt("quantity");

                System.out.println("查询结果如下:");
                System.out.println("ID: " + productID);
                System.out.println("商品名称: " + productName);
                System.out.println("制造商: " + manufacturer);
                System.out.println("型号: " + model);
                System.out.println("进货价格: " + purchasePrice);
                System.out.println("零售价格: " + retailPrice);
                System.out.println("数量: " + quantity);
                System.out.println("------------------------");
            }while (resultSet.next());
            }
        } 
      }
        catch (SQLException e) {
            System.out.println("Failed to query data from the table: " + e.getMessage());
        }
    }
}
    
  
