package core.modelo;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PersistenciaPedidos {
    private static final String ARCHIVO = "pedidos.csv";
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void guardarPedido(Pedido pedido) {
        try (FileWriter writer = new FileWriter(ARCHIVO, true)) {
            String linea = String.join(",",
                pedido.getNumeroOrden(),
                pedido.getCanal().toString(),
                String.valueOf(pedido.total()),
                pedido.getEstado().toString(),
                pedido.getCliente().getNombre(),
                String.valueOf(pedido.getSucursal().getId()),
                LocalDateTime.now().format(FORMATO)
            );
            writer.write(linea + "\n");
            System.out.println("Pedido guardado: " + pedido.getNumeroOrden());
        } catch (IOException e) {
            System.err.println("Error al guardar pedido: " + e.getMessage());
        }
    }
}