package com.utp.dp;

import java.util.Arrays;
import java.util.List;

import productos.*;
import productos.MenuPersonalizado;
import productos.ProductoVendible;
import productos.composite.Combo;
import productos.factory.ProductoFactory;
import restaurante.*;
import restaurante.bridge.*;
import restaurante.builder.*;
import restaurante.memento.*;
import restaurante.observer.*;
import restaurante.pagos.*;
import restaurante.strategy.*;
public class Demo {

    public static void main(String[] args) {

        System.out.println("=== SISTEMA DE GESTION DE PEDIDOS - DEMO COMPLETA ===\n");

        // 1. Configuracion inicial
        Sucursal sucursalCentral = new Sucursal(1, "Restaurante Central", "Av. Principal 123", "555-1234");
        Cliente cliente = new Cliente("C1001", "Ana Rojas");

        // 2. Crear productos usando Factory Method
        Plato plato1 = ProductoFactory.crearPlato("Aeropuerto", 22.5);
        Plato plato2 = ProductoFactory.crearPlato("Tallarines verdes", 18);
        ProductoVendible pizza = ProductoFactory.crearPlato("Pizza Americana", 23);
        Bebida bebida1 = ProductoFactory.crearBebida("Limonada", 4.5, "mediano");

        // 3. Crear combo (Composite)
        Combo combo1 = ProductoFactory.crearCombo("Combo criollo", 15);
        combo1.agregarProducto(plato1);
        combo1.agregarProducto(plato2);
        combo1.agregarProducto(bebida1);

        // 4. Crear menu personalizado (Prototype / Builder)
        MenuPersonalizado menu = ProductoFactory.crearMenuPersonalizado(pizza);
        menu.agregarExtra("Tomate", 5);
        menu.quitarIngrediente("cebolla");

        // 5. Construir pedido con Builder
        Pedido pedido = new PedidoBuilder()
                .conNumeroOrden("PED-1001")
                .conCanal(TipoCanal.PARA_LLEVAR)
                .conCliente(cliente)
                .conSucursal(sucursalCentral)
                .conCodigoRecojo("REC-2024")
                .agregarItem(new ItemPedido(plato1, 1))
                .agregarItem(new ItemPedido(plato2, 2))
                .agregarItem(new ItemPedido(pizza, 1))
                .build();

        // 6. Bridge: procesamiento por canal
        CanalImplementor implementor = new ParaLlevar();
        pedido.setCanalImplementor(implementor);
        pedido.procesarCanal();

        // 7. Registrar observadores (incluyendo notificacion al cliente - D4)
        pedido.agregarObservador(new CocinaObserver());
        pedido.agregarObservador(new CajaObserver());
        pedido.agregarObservador(new AlmacenObserver());
        pedido.agregarObservador(new RepartoObserver());   // nombre original con error tipografico
        pedido.agregarObservador(new ClienteNotificadorObserver());

        // 8. Flujo de estados (A1)
        System.out.println("\n--- FLUJO DEL PEDIDO ---");
        try {
            pedido.confirmarPedido();
            pedido.enPreparacion();
            pedido.listo();
            pedido.enviar();
        } catch (EstadoInvalidoException e) {
            System.err.println("Error en flujo: " + e.getMessage());
        }

        // 9. Metodo de pago (F1)
        System.out.println("\n--- PROCESAMIENTO DE PAGO ---");
        MetodoPago pago = new PagoTarjeta();
        pedido.setMetodoPago(pago);

        double total = pedido.total();
        System.out.println("Total a pagar: S/" + total);
        pago.procesarPago(total);
        System.out.println("Metodo de pago: " + pago.getDescripcion());

        // 10. Promociones avanzadas (C3 y C4)
        System.out.println("\n--- PROMOCIONES AVANZADAS ---");
        Promocion promoPorcentaje = new PromocionPorcentaje(10);
        Promocion promoFijo = new PromocionMontoFijo(15);
        Promocion promo2x1 = new Promocion2x1();

        // Filtros por canal y sucursal (C4)
        Promocion promoParaLlevar = new PromocionPorCanal(TipoCanal.PARA_LLEVAR, promoPorcentaje);
        Promocion promoCentral = new PromocionPorSucursal(sucursalCentral, promoFijo);

        List<Promocion> promociones = Arrays.asList(promoParaLlevar, promoCentral, promo2x1);

        // Aplicar la mejor promocion (C3)
        double totalConDescuento = AplicadorPromociones.aplicarMejorPromocion(pedido, promociones);
        System.out.println("Total original: S/" + pedido.total());
        System.out.println("Total con descuento: S/" + totalConDescuento);
        System.out.println("Ahorro: S/" + (pedido.total() - totalConDescuento));

        // 11. Memento (E1) - guardar y restaurar estado
        System.out.println("\n--- MEMENTO ---");
        Caretaker caretaker = new Caretaker();
        caretaker.guardar(pedido.guardarEstado());
        System.out.println("Estado guardado: " + pedido.getEstado());

        // Simular cancelacion
        pedido.cancelar();
        System.out.println("Estado actual (cancelado): " + pedido.getEstado());

        // Restaurar
        pedido.restaurarEstado(caretaker.obtenerUltimo());
        System.out.println("Estado restaurado: " + pedido.getEstado());

        // 12. Resultados finales
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
    }
}