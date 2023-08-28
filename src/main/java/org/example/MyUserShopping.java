package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class MyUserShopping implements MyAction{
    private static final String DB_Shop = "jdbc:sqlite:Shop.db";
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
        int id = this.scanner.nextInt();

        System.out.print("请输入要加入购物车的商品数量：");
        int quantity = this.scanner.nextInt();

        try (Connection connection = DriverManager.getConnection(DB_Shop);
        PreparedStatement selectStatement = connection.prepareStatement("SELECT quantity FROM Product WHERE id = ?");
         PreparedStatement updateStatement = connection.prepareStatement("UPDATE Product SET quantity = ? WHERE id = ?");
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Shop (id, quantity) VALUES (?, ?)")) {
            int count=0;
            // 查询当前商品的数量
            selectStatement.setInt(1, id);
            ResultSet selectResultSet = selectStatement.executeQuery();
            if (selectResultSet.next()) {
                 count = selectResultSet.getInt("quantity");
            }
            count = count-quantity;
            updateStatement.setInt(1, count);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();
       // 设置要添加的信息
        statement.setInt(1, id); // 设置ID
        statement.setInt(7, quantity);
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
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Shop WHERE productName = ?")) {
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
    System.out.print("请输入需修改的商品编号：");
    int id = this.scanner.nextInt();

    System.out.print("请输入商品数量的变化值：");
    int quantityChange = this.scanner.nextInt();

    try (Connection connection = DriverManager.getConnection(DB_Shop);
         PreparedStatement selectStatement = connection.prepareStatement("SELECT quantity FROM Shop WHERE id = ?");
         PreparedStatement selectStatement1 = connection.prepareStatement("SELECT quantity FROM Product WHERE id = ?");
         PreparedStatement updateStatement = connection.prepareStatement("UPDATE Shop SET quantity = ? WHERE id = ?");
         PreparedStatement updateStatement1 = connection.prepareStatement("UPDATE Product SET quantity = ? WHERE id = ?");
         PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Shop WHERE id = ?")) {
            int newQuantity=0;
            int count=0;
        // 查询当前商品的数量
        selectStatement.setInt(1, id);
        ResultSet selectResultSet = selectStatement.executeQuery();
        if (selectResultSet.next()) {
            int currentQuantity = selectResultSet.getInt("quantity");

            // 计算变化后的数量
            newQuantity = currentQuantity - quantityChange;
            updateStatement.setInt(1, newQuantity);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();

            if (newQuantity <= 0) {
                deleteStatement.setInt(1, id);
                deleteStatement.executeUpdate();
                System.out.println("商品已从数据表中清除");
            } else {
                System.out.println("商品数量已更新");
            }
        } else {
            System.out.println("商品不存在");
        }
        selectStatement1.setInt(1, id);
        ResultSet selectResultSet1 = selectStatement1.executeQuery();
        if (selectResultSet1.next()) {
            count = selectResultSet1.getInt("quantity");
        }
        else {
            System.out.println("商品不存在");
        }
        count = count-newQuantity;
        updateStatement1.setDouble(1, count);
        updateStatement1.setInt(2, id);
        updateStatement.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
   }

   public void  pay(){
    System.out.print("请输入商品编号：");
    int id = this.scanner.nextInt();

    System.out.print("请输入用户名：");
    String username = this.scanner.nextLine();

    try (Connection connection = DriverManager.getConnection(DB_Shop);
     PreparedStatement selectStatement = connection.prepareStatement("SELECT quantity FROM Shop WHERE id = ?");
     PreparedStatement selectStatement1 = connection.prepareStatement("SELECT retailPrice FROM Product WHERE id = ?");
     PreparedStatement updateStatement = connection.prepareStatement("UPDATE manager SET totalAmount = ?, userLevel = ? WHERE username = ?")) {

    int currentQuantity=0;
    // 查询当前商品的数量
    selectStatement.setInt(1, id);
    ResultSet selectResultSet = selectStatement.executeQuery();
    if (selectResultSet.next()) {
        currentQuantity = selectResultSet.getInt("quantity");
    }
    else {
        System.out.println("商品不存在");
        return;
    }
    // 查询当前商品的售价
    double price=0;
    selectStatement1.setInt(1, id);
    ResultSet selectResultSet1 = selectStatement1.executeQuery();
    if (selectResultSet1.next()) {
        price = selectResultSet1.getDouble("retailPrice");
    }
    else {
        System.out.println("商品不存在");
        return;
    }
    
    double totalAmount = currentQuantity * price;
    updateStatement.setDouble(1, totalAmount);
    updateStatement.setString(2, username);
    updateStatement.executeUpdate();

    System.out.println("购物总金额：" + totalAmount);

    // 根据购物总金额区分客户等级并更新到manager数据表
    String userLevel = "";
    if (totalAmount >= 1000) {
        userLevel = "金牌客户";
    } else if (totalAmount >= 500) {
        userLevel = "银牌客户";
    } else {
        userLevel = "铜牌客户";
    }
    updateStatement.setString(1, userLevel);
    updateStatement.setString(2, username);
    updateStatement.executeUpdate();

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
    

    } catch (SQLException e) {
    e.printStackTrace();
    }
 }
  
   public void shoppingHistory(){
    try (Connection connection = DriverManager.getConnection(DB_Shop);
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM Shop")) {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
        int id = resultSet.getInt("id");
        int quantity = resultSet.getInt("quantity");
        System.out.println("购买清单如下:");
        System.out.println("ID: " + id);
        System.out.println("quantity: " + quantity);
        System.out.println("------------------------");
       }
      } catch (SQLException e) {
    System.out.println("Failed to retrieve data from the table: " + e.getMessage());
     }
   }
}
