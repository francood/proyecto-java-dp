package com.utp.restaurante.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoDB {

    // Configuración de la conexión (cámbiala según tu entorno)
    private static final String URL = "jdbc:mysql://localhost:3306/restaurante_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Cargar el driver de MySQL
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Método de prueba para verificar la conexión
    public static void main(String[] args) {
        try {
            Connection cn = getConnection();
            System.out.println("Conexión exitosa a la base de datos.");
            cn.close();
        } catch (Exception e) {
            System.err.println("Error al conectar: " + e.getMessage());
            e.printStackTrace();
        }
    }
}