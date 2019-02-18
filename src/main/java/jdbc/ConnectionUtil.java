package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/it-world?serverTimezone=UTC";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static Connection connection;

    static {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver was not loaded: ");
            throw new MyException(e,"something went wrong");
        } catch (SQLException e) {
            System.out.println("Connection to DB was not established: ");
            throw new MyException(e,"something went wrong");
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
