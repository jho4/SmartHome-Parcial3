package smarthome.patrones.command;

import smarthome.modelo.Alarma;

// Command concreto que representa la operación de desarmar la alarma.
// No se guarda en el historial de acciones reversibles.
public final class ComandoDesarmarAlarma implements Comando {

    private final Alarma alarma;

    public ComandoDesarmarAlarma(Alarma alarma) {

        if (alarma == null) {
            throw new IllegalArgumentException(
                    "El comando necesita una alarma.");
        }

        this.alarma = alarma;
    }

    // Ejecuta la operación sobre la alarma.
    @Override
    public void ejecutar() {
        alarma.desarmar();
    }
}