package DBRelated;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    public static Connection getConnection() throws SQLException{
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/aero";
            String user = "root";
            String password = "usbw";

            con = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    return con;
    }
}
