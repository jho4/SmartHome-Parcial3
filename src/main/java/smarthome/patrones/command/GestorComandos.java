package smarthome.patrones.command;

import java.util.ArrayList;
import java.util.List;


// Se encarga de ejecutar los Commands del hogar y guardar
// las acciones manuales que pueden deshacerse.

public final class GestorComandos {

    private final List<ComandoReversible> historial =
            new ArrayList<>();

    public void ejecutarManual(ComandoReversible comando) {

        if (comando == null) {
            throw new IllegalArgumentException(
                    "El comando no puede ser nulo.");
        }

        comando.ejecutar();

        // Solo se guardan acciones que modificaron el estado.
        if (comando.produjoCambio()) {
            historial.add(comando);
        }
    }

    public void ejecutarAutomatico(Comando comando) {

        if (comando == null) {
            throw new IllegalArgumentException(
                    "El comando no puede ser nulo.");
        }


        limpiarHistorial();
        comando.ejecutar();

    }

    public void limpiarHistorial() {

        historial.clear();
    }

    public boolean puedeDeshacer() {

        return !historial.isEmpty();
    }

    public void deshacerUltimo() {

        if (historial.isEmpty()) {
            throw new IllegalStateException(
                    "No hay una acción manual para deshacer.");
        }

        // La última acción se encuentra terminando el array.
        int ultimaPosicion = historial.size() - 1;

        ComandoReversible ultimo =
                historial.get(ultimaPosicion);

        // Primero restauramos el estado anterior.
        ultimo.deshacer();

        // Para luego eliminar la acción del historial.
        historial.remove(ultimaPosicion);
    }
}