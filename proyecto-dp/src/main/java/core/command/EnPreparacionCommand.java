package core.command;

import core.command.Comando;
import core.modelo.Pedido;


public class EnPreparacionCommand implements Comando {
    private Pedido pedido;

    public EnPreparacionCommand(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public void execute() {
        pedido.enPreparacion();
    }

    @Override
    public void undo() {
        System.out.println("No se puede deshacer preparación directamente.");
    }

    @Override
    public String getNombre() {
        return "En preparación";
    }
}