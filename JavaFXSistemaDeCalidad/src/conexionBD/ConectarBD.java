/*
 * Autor: Brandon Trujillo
 * fecha de creación: 09/12/2020.
 */
package conexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectarBD {

    private static String driver = "com.mysql.jdbc.Driver";
    private static String database = "sgcfei";
    private static String hostname = "localhost";
    private static String port = "3308";
    private static String url= "jdbc:mysql://"+hostname+":"+port+"/"+database+"?allowPublicKeyRetrieval=true&useSSL=false";
    private static String username = "Brandon";
    private static String password = "12345";    
    
    public static Connection abrirConexionMySQL() {
        Connection conn = null;
        
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        return conn;
    }
    
}
