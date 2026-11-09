package smarthome.patrones.state;

import smarthome.modelo.Alarma;

public final class EstadoDesarmada implements EstadoAlarma {

    @Override
    public String obtenerNombre() {

        return "Desarmada";
    }

    @Override
    public void armar(Alarma alarma) {

        alarma.cambiarEstado(new EstadoArmada());
    }

    @Override
    public void desarmar(Alarma alarma) {

        // Sin cambio:
        // la alarma ya está desarmada.
    }

    @Override
    public void detectarMovimiento(Alarma alarma) {

        alarma.informar(
                "Movimiento registrado sin activar la alarma"
        );
    }
}