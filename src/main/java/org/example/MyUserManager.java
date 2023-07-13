package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyUserManager {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public boolean registerUser(String username, String password,String userStatus) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (username, password, userStatus) VALUES (?, ?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, userStatus);
            statement.executeUpdate();
            System.out.println("User registered successfully!");

            return true;
        } catch (SQLException e) {
            System.out.println("Failed to register user: " + e.getMessage());
        }

        return false;
    }

    /**
     * @param username
     * @param password
     * @param userStatus
     * @return
     */
    public boolean login(String username, String password,String userStatus) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    String status = resultSet.getString("userStatus");
                    if (password.equals(storedPassword) && userStatus.equals(status)){
                        System.out.println("Login successful!");
                      return true;
                    }
                    else{
                        System.out.println("Incorrect password or Incorrect userStatus !");
                    }
                }
                else{
                    System.out.println("Username does not exist.");
                }
    }
         catch (SQLException e) {
            System.out.println("Failed to login: " + e.getMessage());
        }

        return false;
    }
}
