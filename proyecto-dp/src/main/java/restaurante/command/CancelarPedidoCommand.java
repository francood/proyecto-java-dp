package restaurante.command;

import restaurante.command.Comando;
import restaurante.Pedido;
import restaurante.Pedido;

public class CancelarPedidoCommand implements Comando {
    private Pedido pedido;

    public CancelarPedidoCommand(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public void execute() {
        pedido.cancelar();
    }

    @Override
    public void undo() {
        System.out.println("No se puede deshacer cancelación.");
    }

    @Override
    public String getNombre() {
        return "Cancelar pedido";
    }
}