package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DatabaseInitializer {
    private static final String DB_URL = "jdbc:sqlite:users.db";
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

    }
}
