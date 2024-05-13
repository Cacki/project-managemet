package sql;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {

    @Test
    public void testConnection() {
        String url = "jdbc:postgresql://localhost:5432/projectmanagement";
        String username = "postgres";
        String password = "postgres";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Assert.assertNotNull(connection);
    }
}
