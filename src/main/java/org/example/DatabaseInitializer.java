package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:sqlite:users.db";
    private static final String DB_Manager = "jdbc:sqlite:manager.db";
    private static final String DB_Product = "jdbc:sqlite:Product.db";
    private static final String DB_managerInfo = "jdbc:sqlite:managerInfo.db";
    private static final String DB_Shop = "jdbc:sqlite:Shop.db";
    public void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT,userMail TEXT)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(DB_managerInfo);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS managerInfo(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT,userMail TEXT)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
        String passWord = "ynuinfo#777";
        passWord = MyPasswordSecurity.PasswordEncryption(passWord);
        try (Connection connection = DriverManager.getConnection(DB_managerInfo);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO managerInfo (username, password, userMail) VALUES (?, ?, ?)")) {
            statement.setString(1, "admin");
            statement.setString(2, passWord);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to register user: " + e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(DB_Manager);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS manager (id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT, userLevel TEXT, totalAmount INTEGER, phoneNumber TEXT, userEmail TEXT)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(DB_Product);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Product (productID TEXT, productName TEXT, manufacturer TEXT, model TEXT, purchasePrice REAL, retailPrice DECIMAL, quantity INTEGER)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(DB_Product);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO Product (productID, productName, manufacturer, model, purchasePrice, retailPrice, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)")){
                    statement.setString(1, "GB123");
                    statement.setString(2, "快乐水");
                    statement.setString(3, "云南昆明");
                    statement.setString(4, "饮料");
                    statement.setDouble(5, 2.0);//2表示价格
                    statement.setDouble(6, 3.0);
                    statement.setString(7, "10箱");
                    statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(DB_Shop);
        Statement statement = connection.createStatement()) {
       String createTableQuery = "CREATE TABLE IF NOT EXISTS Shop (id INTEGER PRIMARY KEY AUTOINCREMENT, productID TEXT, quantity INTEGER)";
       statement.executeUpdate(createTableQuery);
       System.out.println("Database initialized successfully!");
   } catch (SQLException e) {
       System.out.println("Failed to initialize database: " + e.getMessage());
   }
    }
}
