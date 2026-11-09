package smarthome.modelo;

import smarthome.patrones.observer.PublicadorEventos;

import smarthome.patrones.state.EstadoActivada;
import smarthome.patrones.state.EstadoAlarma;
import smarthome.patrones.state.EstadoDesarmada;

import java.util.Objects;

// Contexto de State:
// delega su comportamiento al estado actual.
public final class Alarma {

    private final PublicadorEventos eventos;

    private EstadoAlarma estado =
            new EstadoDesarmada();

    public Alarma(PublicadorEventos eventos) {

        this.eventos = Objects.requireNonNull(eventos);
    }

    public void armar() {

        estado.armar(this);
    }

    public void desarmar() {

        estado.desarmar(this);
    }

    public void detectarMovimiento() {

        estado.detectarMovimiento(this);
    }

    public String obtenerEstado() {

        return estado.obtenerNombre();
    }

    public boolean estaActivada() {

        return estado instanceof EstadoActivada;
    }

    public void cambiarEstado(EstadoAlarma siguiente) {

        estado = Objects.requireNonNull(siguiente);

        eventos.publicar(
                "Alarma",
                "Estado: " + estado.obtenerNombre()
        );
    }

    public void informar(String mensaje) {

        eventos.publicar(
                "Alarma",
                mensaje
        );
    }
}