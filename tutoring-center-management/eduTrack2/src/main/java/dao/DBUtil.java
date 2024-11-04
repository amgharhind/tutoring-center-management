package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
private static final String url = "jdbc:mysql://localhost:3306/dbGestionCentreFinal";
    private static final String username = "root";
    private static final String password = "pbshggbg";

    static {
        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading JDBC driver");
        }
    }

    // Get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}