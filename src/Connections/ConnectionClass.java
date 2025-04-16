package Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

    protected static Connection c;
    protected static String connectionURL = "jdbc:mysql://localhost:3306/foodgrabdb";
    protected static String connectionUser = "root";
    protected static String connectionPassword = "Ygvujmk0";


 //Getter
  public static Connection getConnection() throws SQLException {
      return DriverManager.getConnection(connectionURL, connectionUser, connectionPassword);
  }

}
