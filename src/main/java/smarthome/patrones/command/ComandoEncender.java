package smarthome.patrones.command;

import smarthome.patrones.composite.DispositivoControlable;

// Se utiliza el patrón Command que solicita encender
// un dispositivo o un grupo Composite.

public final class ComandoEncender extends ComandoDispositivo {

    public ComandoEncender(DispositivoControlable destino) {

        super(destino);
    }

    @Override
    protected void aplicar(DispositivoControlable destino) {

        destino.encender();
    }
}