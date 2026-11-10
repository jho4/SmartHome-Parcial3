package smarthome.modelo;

import smarthome.patrones.observer.PublicadorEventos;

// Representa al ventilador que del hogar
// Control manual o con Strategy.

public final class Ventilador extends DispositivoSimple {

    public Ventilador(
            String id,
            String nombre,
            PublicadorEventos eventos) {

        super(id, nombre, eventos);
    }
}