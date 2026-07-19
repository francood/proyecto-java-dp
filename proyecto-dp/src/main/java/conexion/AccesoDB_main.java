package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoDB_main {
public static void main(String[] args) {
    Connection conn = AccesoDB.getConnection();
    if (conn != null) {
        System.out.println("¡Conexión establecida con éxito!");
        //VentanaPrincipal principal = new VentanaPrincipal();
        //principal.setVisible(true);
    } else {
        System.out.println("Falló la conexión.");
    }
    }
}