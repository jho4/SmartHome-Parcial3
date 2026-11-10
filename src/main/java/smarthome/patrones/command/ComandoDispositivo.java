package smarthome.patrones.command;

import smarthome.patrones.composite.DispositivoControlable;

import java.util.ArrayList;
import java.util.List;

// Contiene la lógica común de los comandos reversibles.
// Se encarga de guarda los dispositivos y sus estados anteriores
// por medio de utilizar dos listas que mantienen el mismo orden.

abstract class ComandoDispositivo implements ComandoReversible {

    private final DispositivoControlable destino;

    private final List<DispositivoControlable> individuales =
            new ArrayList<>();

    private final List<Boolean> estadosAnteriores =
            new ArrayList<>();

    private boolean ejecutado;
    private boolean produjoCambio;

    protected ComandoDispositivo(DispositivoControlable destino) {

        if (destino == null) {
            throw new IllegalArgumentException(
                    "El comando necesita un dispositivo.");
        }

        this.destino = destino;
    }

    @Override
    public final void ejecutar() {

        // En cada ejecución comienza con listas limpias.
        individuales.clear();
        estadosAnteriores.clear();

        produjoCambio = false;

        // For para guardar cada dispositivo y su estado anterior.
        for (DispositivoControlable individual
                : destino.obtenerIndividuales()) {

            individuales.add(individual);

            estadosAnteriores.add(
                    individual.estaEncendido()
            );
        }

        // Se ejecuta la operación concreta del comando.
        aplicar(destino);

        // For para comparar los estados actuales con los anteriores.
        for (int i = 0; i < individuales.size(); i++) {

            if (individuales.get(i).estaEncendido()
                    != estadosAnteriores.get(i)) {

                produjoCambio = true;
            }
        }

        ejecutado = true;
    }

    protected abstract void aplicar(
            DispositivoControlable destino);

    @Override
    public final void deshacer() {

        if (!ejecutado) {
            throw new IllegalStateException(
                    "No se puede deshacer un comando sin ejecutar.");
        }

        // For para recorrer los dispositivos por medio de sus posiciones.
        for (int i = 0; i < individuales.size(); i++) {

            DispositivoControlable dispositivo =
                    individuales.get(i);

            boolean estabaEncendido =
                    estadosAnteriores.get(i);

            // Condicional paranRestaurar el estado que tenía cada dispositivo.
            if (estabaEncendido) {
                dispositivo.encender();
            } else {
                dispositivo.apagar();
            }
        }

        ejecutado = false;
    }

    @Override
    public final boolean produjoCambio() {
        return produjoCambio;
    }
}