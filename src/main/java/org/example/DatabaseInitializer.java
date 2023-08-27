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
        String passWord = "ynuinfo#777";
        passWord = MyPasswordSecurity.PasswordEncryption(passWord);
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (username, password, userMail) VALUES (?, ?, ?)")) {
            statement.setString(1, "admin");
            statement.setString(2, passWord);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to register user: " + e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(DB_Manager);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS manager (id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT, userLevel TEXT, registrationTime TEXT, totalAmount INTEGER, phoneNumber TEXT, userEmail TEXT)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(DB_Product);
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Product (productID INTEGER PRIMARY KEY AUTOINCREMENT, productName TEXT, manufacturer TEXT, productionDate TEXT, model TEXT, purchasePrice TEXT, retailPrice TEXT, quantity INTEGER)";
            statement.executeUpdate(createTableQuery);
            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to initialize database: " + e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(DB_Product);
                PreparedStatement statement = connection.prepareStatement("INSERT INTO Product (productName, manufacturer, productionDate, model, purchasePrice, retailPrice, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)")){
                    statement.setString(2, "快乐水");
                    statement.setString(3, "云南昆明");
                    statement.setString(4, "2023/2/03");
                    statement.setString(5, "饮料");
                    statement.setString(6, "2元/瓶");
                    statement.setString(7, "3元/瓶");
                    statement.setString(8, "10箱");
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
