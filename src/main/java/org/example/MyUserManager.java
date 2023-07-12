package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MyUserManager {
    private static final String DB_URL = "jdbc:sqlite:users.db";

    public boolean registerUser(String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Users (username, password) VALUES (?, ?)")) {
            statement.setString(1, username);
            statement.setString(2, password);
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
     * @return
     */
    public boolean login(String username, String password) {
        Scanner scanner = new Scanner(System.in);
        String Input="";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    String storedPassword = resultSet.getString("password");
                    if (password.equals(storedPassword)) {
                        System.out.println("Login successful!");
                        return true;
                    } else {
                        System.out.println("Incorrect password.");
                        while(Input.equals(storedPassword)){
                        System.out.println("请重新输入密码:");
                        Input = scanner.nextLine();
                        }
                    }
                } else {
                    System.out.println("Username does not exist.");
                    while(Input.equals(username)){
                        System.out.println("请重新输入用户名:");
                        Input = scanner.nextLine();
                    }
                }
        } catch (SQLException e) {
            System.out.println("Failed to login: " + e.getMessage());
        }

        return false;
    }
}
