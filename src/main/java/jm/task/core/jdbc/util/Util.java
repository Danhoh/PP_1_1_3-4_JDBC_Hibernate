package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/pp";
    private static final String user = "root";
    private static final String password = "1234567890";
    private static Connection connection = null;

    static {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private Util() {
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
