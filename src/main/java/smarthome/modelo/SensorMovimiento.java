package smarthome.modelo;

import smarthome.patrones.mediator.MediadorHogar;
import smarthome.patrones.observer.PublicadorEventos;

import java.util.Objects;

public final class SensorMovimiento extends Dispositivo {

    private final MediadorHogar mediador;

    private final PublicadorEventos eventos;

    public SensorMovimiento(
            String id,
            String nombre,
            MediadorHogar mediador,
            PublicadorEventos eventos) {

        super(id, nombre);

        this.mediador = Objects.requireNonNull(mediador);

        this.eventos = Objects.requireNonNull(eventos);
    }

    public void simularMovimiento() {

        eventos.publicar(
                obtenerNombre(),
                "Movimiento detectado"
        );

        mediador.recibirMovimiento();
    }
}