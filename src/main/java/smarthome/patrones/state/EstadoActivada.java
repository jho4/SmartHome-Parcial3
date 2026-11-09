package smarthome.patrones.state;

import smarthome.modelo.Alarma;

public final class EstadoActivada implements EstadoAlarma {

    @Override
    public String obtenerNombre() {

        return "Activada";
    }

    @Override
    public void armar(Alarma alarma) {

        throw new IllegalStateException(
                "Primero debes desarmar la alarma activada.");
    }

    @Override
    public void desarmar(Alarma alarma) {

        alarma.cambiarEstado(new EstadoDesarmada());
    }

    @Override
    public void detectarMovimiento(Alarma alarma) {

        alarma.informar(
                "Otro movimiento mientras la alarma sigue activada"
        );
    }
}