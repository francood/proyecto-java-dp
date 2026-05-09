package restaurante;

import productos.Plato;
import productos.Bebida;
import productos.Combo;
import productos.Adicionable;
import productos.Producto;
import productos.ProductoVendible;

public class Restaurante {

    public static void main(String[] args) {

        // 1.Crear productos
        Plato plato1 = new Plato("Aeropuerto", 22.5);
        Plato plato2 = new Plato("Tallarines verdes", 18);
        Producto pizza = new Plato("Pizza Americana", 23);
        Bebida bebida1 = new Bebida("Limonada", 4.5, "mediano");
        Adicionable quesoExtra = new Adicionable(plato1, "Queso extra", 5);
        System.out.println("plato 1: " + quesoExtra.getNombre());

        // 2.Crear combo con descuento 15%
        Combo combo1 = new Combo("Combo criollo", 15);
        combo1.agregarProducto(plato1);
        combo1.agregarProducto(plato2);
        combo1.agregarProducto(bebida1);
        System.out.println("Precio del combo: " + combo1.getPrecio());

        // 3.Crear pedido y sucursal
        Cliente cliente1 = new Cliente("C1001", "Ana Rojas");
        Restaurante sucursal = new Restaurante();

        //4. Crear Pedido
        Pedido pedido1 = new Pedido("PED-412", TipoCanal.PARA_LLEVAR, cliente1, sucursal);

        //Para llevar
        pedido1.setCodigoRecojo("REC-001");

        CanalProcesador procesar = new ParaLlevar();
        procesar.procesar(pedido1);

        // 5.Crear ítems y agregar al pedido
        ItemPedido item1 = new ItemPedido(plato1, 1);
        ItemPedido item2 = new ItemPedido(plato2, 3);
        ItemPedido item3 = new ItemPedido(pizza, 2);
        pedido1.agregarItem(item1);
        pedido1.agregarItem(item2);
        pedido1.agregarItem(item3);

        //6. Cambio de estados
        try {
            pedido1.confirmarPedido();
            pedido1.enPreparacion();
            pedido1.listo();
        } catch (EstadoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Personalizar plato1 (sin costo adicional)
        plato1.añadirExtras("Brocoli");
        plato1.quitarIngredientes("cebolla");

        // 5.Aplicar promoción del 5% sobre el total del pedido
        PromocionesTemporada promo1 = new PromocionesTemporada("Jueves de promo", 5);
        double totalConDescuento = promo1.aplicarPromocion(pedido1.getItems());

        // 6.Mostrar resultados
        System.out.println("Subtotal sin descuento: " + pedido1.total());
        System.out.println("Total con descuento: " + totalConDescuento);
        System.out.println("Ahorro: " + (pedido1.total() - totalConDescuento));

        //Motrar Historial y eStados
        System.out.println("Estado actual: " + pedido1.getEstado());
        System.out.println("Historial: " + pedido1.getHistorialEstados());

    }
}
