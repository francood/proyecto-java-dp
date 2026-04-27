package restaurante;

import restaurante.productos.Plato;
import restaurante.productos.Bebida;
import restaurante.productos.Combo;
import restaurante.productos.Adicionable;
import restaurante.productos.ProductoSolido;
import restaurante.productos.ProductoVendible;

public class Restaurante {
    public static void main(String[] args) {
        
        // 1.Crear productos
        ProductoSolido plato1 = new Plato("Aeropuerto", 22.5);
        ProductoSolido plato2 = new Plato("Tallarines verdes", 18);
        ProductoSolido pizza = new Plato("Pizza Americana", 23);
        ProductoVendible bebida1 = new Bebida("Limonada", 4.5, "mediano");
        Adicionable quesoExtra = new Adicionable("Queso extra", 5);
        
        // 2.Crear combo con descuento 15%
        Combo combo1 = new Combo("Combo criollo", 15);
        combo1.agregarProducto(plato1);
        combo1.agregarProducto(plato2);
        combo1.agregarProducto(bebida1); 
        System.out.println("Precio del combo: " + combo1.getPrecio());
        
        // 3.Crear pedido
        Cliente cliente1 = new Cliente("C1001", "Ana Rojas");
        Pedido pedido1 = new Pedido("PED-412", TipoCanal.PARA_LLEVAR, cliente1);
        
        // 4.Crear ítems y agregar al pedido
        ItemPedido item1 = new ItemPedido(plato1, 1);
        ItemPedido item2 = new ItemPedido(plato2, 3);
        ItemPedido item3 = new ItemPedido(pizza, 2);
        item3.agregarAdicionable(quesoExtra);  // queso extra para cada pizza
        pedido1.agregarItem(item1);
        pedido1.agregarItem(item2);
        pedido1.agregarItem(item3);
        
        // Personalizar plato1 (sin costo adicional)
        plato1.añadirExtras("Brocoli");
        
        // 5.Aplicar promoción del 5% sobre el total del pedido
        PromocionesTemporada promo1 = new PromocionesTemporada("Jueves de promo", 5);
        double totalConDescuento = promo1.aplicarPromocion(pedido1.getItems());
        
        // 6.Mostrar resultados
        System.out.println("Subtotal sin descuento: " + pedido1.total());
        System.out.println("Total con descuento: " + totalConDescuento);
        System.out.println("Ahorro: " + (pedido1.total() - totalConDescuento));
        
        
    }
}