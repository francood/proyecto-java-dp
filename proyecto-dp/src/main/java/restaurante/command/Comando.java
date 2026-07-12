package restaurante.command;

public interface Comando {
    void execute();
    void undo();
    String getNombre();
}