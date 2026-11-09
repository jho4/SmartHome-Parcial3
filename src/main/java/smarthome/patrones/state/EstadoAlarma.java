package smarthome.patrones.state;

import smarthome.modelo.Alarma;

public interface EstadoAlarma {

    String obtenerNombre();

    void armar(Alarma alarma);

    void desarmar(Alarma alarma);

    void detectarMovimiento(Alarma alarma);
}