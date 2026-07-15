package core.modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class GeneradorReportes {
    private static final String ARCHIVO = "pedidos.csv";
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void generarReporte() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            int totalPedidos = 0;
            int cancelados = 0;
            Map<String, Integer> productosVendidos = new HashMap<>();

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                totalPedidos++;
                if (datos[3].equals("Cancelado")) {
                    cancelados++;
                }
                // Simplificacion: no leemos items del CSV, solo contamos pedidos
            }

            System.out.println("Total pedidos: " + totalPedidos);
            System.out.println("Pedidos cancelados: " + cancelados);
            System.out.println("Tasa de cancelacion: " +
                (totalPedidos > 0 ? (cancelados * 100.0 / totalPedidos) : 0) + "%");

        } catch (IOException e) {
            System.err.println("No se pudieron leer los reportes: " + e.getMessage());
        }
    }

    public static void generarReportePorSucursal(int idSucursal) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            int totalPedidos = 0;
            int cancelados = 0;

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (Integer.parseInt(datos[5]) == idSucursal) {
                    totalPedidos++;
                    if (datos[3].equals("Cancelado")) {
                        cancelados++;
                    }
                }
            }

            System.out.println("Total pedidos para sucursal " + idSucursal + ": " + totalPedidos);
            System.out.println("Cancelados: " + cancelados);

        } catch (IOException e) {
            System.err.println("Error al leer reporte por sucursal: " + e.getMessage());
        }
    }

    public static void generarReportePorFecha(LocalDate desde, LocalDate hasta) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            int totalPedidos = 0;

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                LocalDateTime fecha = LocalDateTime.parse(datos[6], FORMATO);
                LocalDate fechaPedido = fecha.toLocalDate();

                if ((fechaPedido.isEqual(desde) || fechaPedido.isAfter(desde)) &&
                    (fechaPedido.isEqual(hasta) || fechaPedido.isBefore(hasta))) {
                    totalPedidos++;
                }
            }

            System.out.println("Pedidos entre " + desde + " y " + hasta + ": " + totalPedidos);

        } catch (IOException e) {
            System.err.println("Error al leer reporte por fecha: " + e.getMessage());
        }
    }
}