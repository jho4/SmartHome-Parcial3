package smarthome.patrones.command;

import smarthome.modelo.Alarma;

import java.util.Objects;

// Comando no reversible:
// no se deshace mediante el historial manual.
public final class ComandoArmarAlarma implements Comando {

    private final Alarma alarma;

    public ComandoArmarAlarma(Alarma alarma) {

        this.alarma = Objects.requireNonNull(alarma);
    }

    @Override
    public void ejecutar() {

        alarma.armar();
    }
}