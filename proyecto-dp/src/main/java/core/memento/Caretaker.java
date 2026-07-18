package core.memento;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    private List<Memento> historial = new ArrayList<>();

    public void guardar(Memento memento) {
        historial.add(memento);
    }

    public Memento obtenerUltimo() {
        if (historial.isEmpty()) return null;
        return historial.get(historial.size() - 1);
    }

    public Memento obtener(int indice) {
        if (indice < 0 || indice >= historial.size()) return null;
        return historial.get(indice);
    }

    public void mostrarHistorial() {
        System.out.println("Historial de estados guardados:");
        for (int i = 0; i < historial.size(); i++) {
            System.out.println(i + ": " + historial.get(i).getEstado() + " - " + historial.get(i).getNumeroOrden());
        }
    }
}