package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoDB {

    public static Connection getConnection() { 
        
    String conexionURL = "jdbc:sqlserver://localhost:1433;databaseName=restaurante_db;user=sa;password=123456;encrypt=true;trustServerCertificate=true;loginTimeout=30;";
        
        try {
            Connection con = DriverManager.getConnection(conexionURL);
            System.out.println("Conectado a la bd correctamente.");
            return con;
        } catch (SQLException ex) {
            System.out.println("Error al conectar: " + ex.toString());
            return null;
        }
    }
}