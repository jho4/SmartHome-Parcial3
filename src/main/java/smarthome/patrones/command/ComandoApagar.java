package smarthome.patrones.command;

import smarthome.patrones.composite.DispositivoControlable;

// Se utilizando patrón Command que solicita apagar
// un dispositivo o un grupo Composite.

public final class ComandoApagar extends ComandoDispositivo {

    public ComandoApagar(DispositivoControlable destino) {

        super(destino);
    }

    @Override
    protected void aplicar(DispositivoControlable destino) {

        destino.apagar();
    }
}