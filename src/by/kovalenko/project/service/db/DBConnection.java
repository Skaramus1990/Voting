
package by.kovalenko.project.service.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/voting";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "admin";
    
    protected static Connection getDBconnection() throws SQLException{
        Connection con = null;
        try {
            Class.forName(DRIVER);
        }catch (ClassNotFoundException c){
            System.out.println("ошибка подключения" + c);
        }    
        try{
            con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        }catch(SQLException ex){
            System.out.println("Ошибка подключения" + ex);
        }
        return con;
    }
}
