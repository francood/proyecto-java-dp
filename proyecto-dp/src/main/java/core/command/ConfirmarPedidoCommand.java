package core.command;

import core.command.Comando;
import core.modelo.Pedido;

public class ConfirmarPedidoCommand implements Comando {
    private Pedido pedido;

    public ConfirmarPedidoCommand(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public void execute() {
        pedido.confirmarPedido();
    }

    @Override
    public void undo() {
        System.out.println("No se puede deshacer confirmación directamente.");
    }

    @Override
    public String getNombre() {
        return "Confirmar pedido";
    }
}