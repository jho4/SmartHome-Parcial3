package smarthome.modelo;

import smarthome.patrones.mediator.MediadorHogar;
import smarthome.patrones.observer.PublicadorEventos;

// Sensor de movimiento simulado.
// Publica una notificación (Observer) y comunica el movimiento (Mediator).
// No conoce directamente a la alarma.
public final class SensorMovimiento extends Dispositivo {

    private final MediadorHogar mediador;

    private final PublicadorEventos eventos;

    public SensorMovimiento(
            String id,
            String nombre,
            MediadorHogar mediador,
            PublicadorEventos eventos) {

        // Inicializamos los datos comunes de Dispositivo.
        super(id, nombre);

        if (mediador == null || eventos == null) {
            throw new IllegalArgumentException(
                    "El sensor necesita mediador y eventos.");
        }

        this.mediador = mediador;
        this.eventos = eventos;
    }

    // Simula que el sensor detecta movimiento.
    public void simularMovimiento() {

        // Observer comunica que se detectó movimiento.
        eventos.publicar(obtenerNombre(), "Movimiento detectado");

        // Mediator coordina la reacción de seguridad.
        mediador.recibirMovimiento();
    }
}