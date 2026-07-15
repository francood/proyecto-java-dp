package com.utp.dp;

import core.bridge.CanalImplementor;
import core.bridge.ParaLlevar;
import core.builder.PedidoBuilder;
import core.eventos.AlmacenObserver;
import core.eventos.CajaObserver;
import core.eventos.ClienteNotificadorObserver;
import core.eventos.CocinaObserver;
import core.eventos.RepartoObserver;
import core.exceptions.EstadoInvalidoException;
import core.exceptions.StockInsuficienteException;
import core.factory.ProductoFactory;
import core.memento.Caretaker;
import core.modelo.Cliente;
import core.modelo.EstadoOrden;
import core.modelo.GeneradorReportes;
import core.modelo.Inventario;
import core.modelo.Item;
import core.modelo.ItemPedido;
import core.modelo.Pedido;
import core.modelo.PersistenciaPedidos;
import core.modelo.Sucursal;
import core.modelo.TipoCanal;
import core.pagos.PagoTarjeta;
import core.pagos.MetodoPago;
import core.productos.Bebida;
import core.productos.Plato;
import java.util.Arrays;
import java.util.List;

import core.productos.ProductoVendible;
import core.promociones.AplicadorPromociones;
import core.promociones.Promocion2x1;
import core.promociones.PromocionMontoFijo;
import core.promociones.PromocionPorCanal;
import core.promociones.PromocionPorSucursal;
import core.promociones.PromocionPorcentaje;
import core.strategy.Promocion;


public class Demo {

public static void main(String[] args) {

        System.out.println("=== SISTEMA DE GESTION DE PEDIDOS - DEMO COMPLETA ===\n");

        // 1. Configuracion inicial
        Sucursal sucursalCentral = new Sucursal(1, "Restaurante Central", "Av. Principal 123", "555-1234");
        Cliente cliente = new Cliente("C1001", "Ana Rojas");

        // 2. Cargar inventario inicial (E4)
        System.out.println("--- CARGANDO INVENTARIO ---");
        Inventario inventario = Inventario.getInstancia();

        Plato plato1 = ProductoFactory.crearPlato("Aeropuerto", 22.5);
        Plato plato2 = ProductoFactory.crearPlato("Tallarines verdes", 18);
        ProductoVendible pizza = ProductoFactory.crearPlato("Pizza Americana", 23);
        Bebida bebida1 = ProductoFactory.crearBebida("Limonada", 4.5, "mediano");

        inventario.agregarStock(plato1, 10);
        inventario.agregarStock(plato2, 5);
        inventario.agregarStock(pizza, 3);
        inventario.agregarStock(bebida1, 8);

        inventario.mostrarInventario();

        // 3. Crear items del pedido
        List<Item> items = Arrays.asList(
                new ItemPedido(plato1, 1),
                new ItemPedido(plato2, 2),
                new ItemPedido(pizza, 1)
        );

        // 4. Construir pedido con Builder
        Pedido pedido = new PedidoBuilder()
                .conNumeroOrden("PED-1001")
                .conCanal(TipoCanal.PARA_LLEVAR)
                .conCliente(cliente)
                .conSucursal(sucursalCentral)
                .conCodigoRecojo("REC-2024")
                .agregarItem(items.get(0))
                .agregarItem(items.get(1))
                .agregarItem(items.get(2))
                .build();

        // 5. Bridge: procesamiento por canal
        CanalImplementor implementor = new ParaLlevar();
        pedido.setCanalImplementor(implementor);
        pedido.procesarCanal();

        // 6. Registrar observadores (D2 + D4)
        pedido.agregarObservador(new CocinaObserver());
        pedido.agregarObservador(new CajaObserver());
        pedido.agregarObservador(new AlmacenObserver());
        pedido.agregarObservador(new RepartoObserver());
        pedido.agregarObservador(new ClienteNotificadorObserver());

        // 7. Verificar stock ANTES de confirmar (E4)
        System.out.println("\n--- VERIFICANDO STOCK ANTES DE CONFIRMAR ---");
        try {
            inventario.verificarStock(pedido);
            System.out.println("Stock suficiente para todos los productos.");
        } catch (StockInsuficienteException e) {
            System.err.println("Error de stock: " + e.getMessage());
            return;
        }

        // 8. Flujo de estados (A1)
        System.out.println("\n--- FLUJO DEL PEDIDO ---");
        try {
            pedido.confirmarPedido();  // Aqui se descuenta el stock (E4)
            pedido.enPreparacion();
            pedido.listo();
            pedido.enviar();
        } catch (EstadoInvalidoException | StockInsuficienteException e) {
            System.err.println("Error en flujo: " + e.getMessage());
            return;
        }

        // 9. Mostrar inventario DESPUES de confirmar (E4)
        System.out.println("\n--- INVENTARIO DESPUES DE CONFIRMAR ---");
        inventario.mostrarInventario();

        // 10. Metodo de pago (F1)
        System.out.println("\n--- PROCESAMIENTO DE PAGO ---");
        MetodoPago pago = new PagoTarjeta();
        pedido.setMetodoPago(pago);
        double total = pedido.total();
        System.out.println("Total a pagar: S/" + total);
        pago.procesarPago(total);
        System.out.println("Metodo de pago: " + pago.getDescripcion());

        // 11. Promociones avanzadas (C3 + C4)
        System.out.println("\n--- PROMOCIONES AVANZADAS ---");
        Promocion promoPorcentaje = new PromocionPorcentaje(10);
        Promocion promoFijo = new PromocionMontoFijo(15);
        Promocion promo2x1 = new Promocion2x1();
        Promocion promoParaLlevar = new PromocionPorCanal(TipoCanal.PARA_LLEVAR, promoPorcentaje);
        Promocion promoCentral = new PromocionPorSucursal(sucursalCentral, promoFijo);
        List<Promocion> promociones = Arrays.asList(promoParaLlevar, promoCentral, promo2x1);
        double totalConDescuento = AplicadorPromociones.aplicarMejorPromocion(pedido, promociones);
        System.out.println("Total original: S/" + pedido.total());
        System.out.println("Total con descuento: S/" + totalConDescuento);
        System.out.println("Ahorro: S/" + (pedido.total() - totalConDescuento));

        // 12. Memento (E1) - se guarda el estado actual (Enviado) y luego se intenta cancelar
        System.out.println("\n--- MEMENTO ---");
        Caretaker caretaker = new Caretaker();
        caretaker.guardar(pedido.guardarEstado());
        System.out.println("Estado guardado: " + pedido.getEstado());

        // Intentar cancelar (falla porque el pedido ya esta Enviado)
        try {
            pedido.cancelar();
            System.out.println("Pedido cancelado (no deberia llegar aqui)");
        } catch (EstadoInvalidoException e) {
            System.out.println("No se pudo cancelar: " + e.getMessage());
            System.out.println("El pedido ya esta en estado Enviado, no se puede cancelar.");
        }

        // Restaurar el estado guardado (vuelve a Enviado)
        pedido.restaurarEstado(caretaker.obtenerUltimo());
        System.out.println("Estado restaurado mediante Memento: " + pedido.getEstado());

        // 13. Persistencia en CSV (E2)
        System.out.println("\n--- PERSISTENCIA EN CSV ---");
        PersistenciaPedidos.guardarPedido(pedido);
        System.out.println("Pedido guardado en pedidos.csv");

        // 14. Generar reportes (E3 + E5)
        System.out.println("\n--- REPORTES ---");
        System.out.println("=== REPORTE GENERAL ===");
        GeneradorReportes.generarReporte();

        System.out.println("\n=== REPORTE POR SUCURSAL (ID 1) ===");
        GeneradorReportes.generarReportePorSucursal(1);

        System.out.println("\n=== REPORTE POR FECHA (hoy) ===");
        java.time.LocalDate hoy = java.time.LocalDate.now();
        GeneradorReportes.generarReportePorFecha(hoy.minusDays(1), hoy.plusDays(1));

        // 15. Resultados finales
        System.out.println("\n--- RESULTADOS FINALES ---");
        System.out.println("Numero de orden: " + pedido.getNumeroOrden());
        System.out.println("Cliente: " + pedido.getCliente().getNombre());
        System.out.println("Estado actual: " + pedido.getEstado());
        System.out.println("Historial: " + pedido.getHistorialEstados());
        System.out.println("Total del pedido: S/" + pedido.total());
        System.out.println("Metodo de pago: " + pedido.getMetodoPago().getDescripcion());

        System.out.println("\nHistorial completo de estados:");
        for (EstadoOrden e : pedido.getHistorialEstados()) {
            System.out.println("   -> " + e);
        }

        System.out.println("\n=== DEMO COMPLETADA EXITOSAMENTE ===");
    }
}