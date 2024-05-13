package org.example.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
    private static String url = "jdbc:postgresql://localhost:5432/projectmanagement";
    private static String username = "postgres";
    private static String password = "postgres";
    public static Connection connection;

    // Initialize the connection when the class is loaded
    static {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to the database. Exiting program...");
            System.exit(1);
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
