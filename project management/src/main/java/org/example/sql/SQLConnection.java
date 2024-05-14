package org.example.sql;

import org.example.utils.SQLMessages;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLConnection {
    private static final Logger LOGGER = Logger.getLogger(SQLConnection.class.getName());
    private static String url = "jdbc:postgresql://localhost:5432/projectmanagement";
    private static String username = "postgres";
    private static String password = "postgres";
    public static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.log(Level.SEVERE, SQLMessages.FAILED_TO_CONNECT_TO_DB);
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
