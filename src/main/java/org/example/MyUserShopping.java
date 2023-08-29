package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class MyUserShopping implements MyAction{
    private static final String DB_Shop = "jdbc:sqlite:Shop.db";
    private static final String DB_Manager = "jdbc:sqlite:manager.db";
    private static final String DB_Product = "jdbc:sqlite:Product.db";
    private static final String ACTION_NAME = "shopping";
    private Scanner scanner;

    public MyUserShopping(Scanner scanner) {
        this.scanner = scanner;
    }
    
    @Override
    public String getActionName() {
        return MyUserShopping.ACTION_NAME;
    }

    @Override
    public void run(String[] args) {
        System.out.println("欢迎进入购物界面!");

        System.out.println("加入购物车：add,移出购物车:delete,修改购物车中的商品:change,付账：pay,查看购物历史:check");
        String Input = this.scanner.nextLine();
        switch(Input){
            case "add": 
                         addProduct();
                         break;
            case "delete":
                        deleteProduct();
                        break;
            case "change":
                          changeProduct();
                          break;
            case "pay":
                         pay();
                         break;
            case "check":
                        shoppingHistory();
                        break;
        }
    }

    public void addProduct(){
        System.out.print("请输入要加入购物车的商品编号：");
        String productID = this.scanner.nextLine();

        System.out.print("请输入要加入购物车的商品数量：");
        int quantity = Integer.parseInt(this.scanner.nextLine());
      
        System.out.print("请确认你的用户名：");
        String name = this.scanner.nextLine();

        try (Connection connection = DriverManager.getConnection(DB_Shop);
            Connection connection1 = DriverManager.getConnection(DB_Product);
            PreparedStatement selectStatement = connection1.prepareStatement("SELECT quantity FROM Product WHERE productID = ?");
            PreparedStatement updateStatement = connection1.prepareStatement("UPDATE Product SET quantity = ? WHERE productID = ?");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Shop (name，productID, quantity) VALUES (?, ?, ?)")) {
            int count=0;
            // 查询当前商品的数量
            selectStatement.setString(1, productID);
            ResultSet selectResultSet = selectStatement.executeQuery();
            if (selectResultSet.next()) {
                 count = selectResultSet.getInt("quantity");
            }
            count = count-quantity;
            updateStatement.setInt(1, count);
            updateStatement.setString(2, productID);
            updateStatement.executeUpdate();
       // 设置要添加的信息
       statement.setString(1, name);
        statement.setString(2, productID); // 设置ID
        statement.setInt(3, quantity);
      // 执行插入操作
      int rowsAffected = statement.executeUpdate();
      System.out.println("已添加 " + rowsAffected + " 条信息");
    }
    catch (SQLException e) {
        e.printStackTrace();
          }
   }

   public void deleteProduct(){
    try (Connection connection = DriverManager.getConnection(DB_Shop);
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Shop WHERE name = ?")) {
       // 根据商品名称删除商品信息
       System.out.print("输入你的用户名:");
       String name = this.scanner.nextLine();

       // 确认是否继续删除
       System.out.print("Are you sure you want to delete row with name " + name + "? (Y/N): ");
       String confirmation = this.scanner.nextLine();
       if (confirmation.equalsIgnoreCase("Y")) {
           statement.setString(1, name);
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
    System.out.print("请确认你的用户名：");
        String name = this.scanner.nextLine();

        System.out.print("请输入要修改数量的商品编号：");
        String productID = this.scanner.nextLine();

       System.out.print("请输入商品数量的变化值：");
       int quantityChange = Integer.parseInt(this.scanner.nextLine());

    try (Connection connection = DriverManager.getConnection(DB_Shop);
        Connection connection1 = DriverManager.getConnection(DB_Product);
         PreparedStatement selectStatement = connection.prepareStatement("SELECT quantity FROM Shop WHERE name = ?");
         PreparedStatement selectStatement1 = connection1.prepareStatement("SELECT quantity FROM Product WHERE productID = ?");
         PreparedStatement updateStatement = connection.prepareStatement("UPDATE Shop SET quantity = ? WHERE productID = ?");
         PreparedStatement updateStatement1 = connection1.prepareStatement("UPDATE Product SET quantity = ? WHERE productID = ?");
         PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Shop WHERE productID = ?")) {
            int newQuantity=0;
            int count=0;
        // 查询当前商品的数量
        selectStatement.setString(1, name);
        ResultSet selectResultSet = selectStatement.executeQuery();
        if (selectResultSet.next()) {
            int currentQuantity = selectResultSet.getInt("quantity");

            // 计算变化后的数量
            newQuantity = currentQuantity - quantityChange;
            updateStatement.setInt(1, newQuantity);
            updateStatement.setString(2, productID);
            updateStatement.executeUpdate();

            if (newQuantity <= 0) {
                deleteStatement.setString(1, productID);
                deleteStatement.executeUpdate();
                System.out.println("商品已从数据表中清除");
            } else {
                System.out.println("商品数量已更新");
            }
        } else {
            System.out.println("商品不存在");
        }
        selectStatement1.setString(1, productID);
        ResultSet selectResultSet1 = selectStatement1.executeQuery();
        if (selectResultSet1.next()) {
            count = selectResultSet1.getInt("quantity");
        }
        else {
            System.out.println("商品不存在");
        }
        count = count-newQuantity;
        updateStatement1.setDouble(1, count);
        updateStatement1.setString(2, productID);
        updateStatement.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
   }

   public void  pay(){
    
    double totalAmount = 0.0;
    double totalAmount1 = 0.0;
      System.out.print("请输入用户名：");
      String name = this.scanner.nextLine();

      System.out.print("请输入商品编号：");
      String productID = this.scanner.nextLine();
      while (!productID.equals("q")) {
        try (Connection connection = DriverManager.getConnection(DB_Shop);
         Connection connection1 = DriverManager.getConnection(DB_Manager);
          Connection connection2 = DriverManager.getConnection(DB_Product);
           PreparedStatement selectStatement = connection.prepareStatement("SELECT quantity FROM Shop WHERE name = ?");
            PreparedStatement selectStatement1 = connection2.prepareStatement("SELECT retailPrice FROM Product WHERE productID = ?");
            PreparedStatement updateStatement = connection1.prepareStatement("UPDATE manager SET totalAmount = ?, userLevel = ? WHERE username = ?")) {

         int currentQuantity=0;
         // 查询当前商品的数量
        selectStatement.setString(1, name);
        ResultSet selectResultSet = selectStatement.executeQuery();
         if (selectResultSet.next()) {
         currentQuantity = selectResultSet.getInt("quantity");
        }
        else {
          System.out.println("商品不存在");
          continue; // 继续下一轮循环
         }
          // 查询当前商品的售价
          double price=0;
          selectStatement1.setString(1, productID);
          ResultSet selectResultSet1 = selectStatement1.executeQuery();
         if (selectResultSet1.next()) {
         price = selectResultSet1.getDouble("retailPrice");
        }
       else {
        System.out.println("商品不存在");
        continue; // 继续下一轮循环
       }
         totalAmount += currentQuantity * price;
         totalAmount1 = currentQuantity * price;

        System.out.println("购物总金额：" + totalAmount1);

        // 使用支付宝、微信和银行卡来模拟付账
       System.out.println("请根据购物总金额付账！");
        System.out.println("支付渠道有：支付宝：Alipay、微信:WeChat、银行卡:Card");
        String input=this.scanner.nextLine();

        if(input.equals("WeChat")){
               System.out.println("微信支付成功！");
        }
            else if(input.equals("Alipay")){
             System.out.println("支付宝支付成功！");
          }
          else if(input.equals("Card")){
               System.out.println("银行卡支付成功！");
             }
             // 根据购物总金额区分客户等级并更新到manager数据表
          String userLevel = "";
          if (totalAmount >= 1000) {
           userLevel = "金牌客户";
         } else if (totalAmount >= 500) {
          userLevel = "银牌客户";
          } else {
            userLevel = "铜牌客户";
          }

         updateStatement.setDouble(1, totalAmount);
         updateStatement.setString(2, userLevel);
         updateStatement.setString(3, name);
         updateStatement.executeUpdate();
     } catch (SQLException e) {
            e.printStackTrace();
            }
            System.out.println("若购物车中有多种商品。");
            System.out.print("请输入商品编号（输入q退出）：");
            productID = this.scanner.nextLine();

        }
 }
  
   public void shoppingHistory(){
    try (Connection connection = DriverManager.getConnection(DB_Shop);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Shop")) {
        ResultSet resultSet = statement.executeQuery();
        if(!resultSet.next()){
            System.out.println("无购买历史");
           }
           else{
         do {
        String name = resultSet.getString("name");
         String productID = resultSet.getString("productID");
         int quantity = resultSet.getInt("quantity");
         System.out.println("购买清单如下:");
         System.out.println("name: " + name);
         System.out.println("ID: " + productID);
         System.out.println("quantity: " + quantity);
         System.out.println("------------------------");
       } while (resultSet.next());
    }
      } catch (SQLException e) {
    System.out.println("Failed to retrieve data from the table: " + e.getMessage());
     }
   }
}
