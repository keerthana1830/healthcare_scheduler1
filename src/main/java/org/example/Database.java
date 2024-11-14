package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public class ConnectionTest {
        public static void main(String[] args) {
            String url = "jdbc:mysql://localhost:3306/healthcaresystem";
            String username = "root";
            String password = "root";

            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                if (connection != null) {
                    System.out.println("Database connected successfully!");
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Database connection error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
