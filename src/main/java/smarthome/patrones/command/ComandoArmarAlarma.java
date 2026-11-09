package smarthome.patrones.command;

import smarthome.modelo.Alarma;

// Command concreto que representa la operación de armar la alarma.
// Es una acción de seguridad y no se guarda en el historial de deshacer.
public final class ComandoArmarAlarma implements Comando {

    private final Alarma alarma;

    public ComandoArmarAlarma(Alarma alarma) {

        if (alarma == null) {
            throw new IllegalArgumentException(
                    "El comando necesita una alarma.");
        }

        this.alarma = alarma;
    }

    // Ejecuta la operación sobre la alarma.
    @Override
    public void ejecutar() {
        alarma.armar();
    }
}