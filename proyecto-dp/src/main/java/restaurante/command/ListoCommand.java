package restaurante.command;

import restaurante.command.Comando;
import restaurante.Pedido;
import restaurante.Pedido;

public class ListoCommand implements Comando {
    private Pedido pedido;

    public ListoCommand(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public void execute() {
        pedido.listo();
    }

    @Override
    public void undo() {
        System.out.println("No se puede deshacer estado listo.");
    }

    @Override
    public String getNombre() {
        return "Listo para entrega";
    }
}