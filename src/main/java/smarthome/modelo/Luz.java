package smarthome.modelo;

import smarthome.patrones.observer.PublicadorEventos;

// Representa a la luz del hogar.
// Hereda las operaciones de encendido y apagado.

public final class Luz extends DispositivoSimple {

    public Luz(
            String id,
            String nombre,
            PublicadorEventos eventos) {

        super(id, nombre, eventos);
    }
}