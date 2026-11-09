package smarthome.patrones.state;

import com.smarthome.modelo.Alarma;

public final class EstadoArmada implements EstadoAlarma {

    @Override
    public String obtenerNombre() {

        return "Armada";
    }

    @Override
    public void armar(Alarma alarma) {

        // Sin cambio:
        // la alarma ya está armada.
    }

    @Override
    public void desarmar(Alarma alarma) {

        alarma.cambiarEstado(new EstadoDesarmada());
    }

    @Override
    public void detectarMovimiento(Alarma alarma) {

        alarma.cambiarEstado(new EstadoActivada());
    }
}