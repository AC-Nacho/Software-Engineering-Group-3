package service;
import java.sql.*;


public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/healthcaretest?user=root&password=TheCheeseBurgerBottom98&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
