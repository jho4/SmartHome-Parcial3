package smarthome.patrones.command;

import smarthome.modelo.Alarma;

import java.util.Objects;

public final class ComandoDesarmarAlarma implements Comando {

    private final Alarma alarma;

    public ComandoDesarmarAlarma(Alarma alarma) {

        this.alarma = Objects.requireNonNull(alarma);
    }

    @Override
    public void ejecutar() {

        alarma.desarmar();
    }
}