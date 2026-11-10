package smarthome.servicio;

import smarthome.modelo.Ventilador;

import smarthome.patrones.command.ComandoApagar;
import smarthome.patrones.command.ComandoEncender;
import smarthome.patrones.command.GestorComandos;

import smarthome.patrones.observer.PublicadorEventos;

import smarthome.patrones.strategy.EstrategiaAhorro;
import smarthome.patrones.strategy.EstrategiaClimatizacion;

import java.util.Objects;

public final class ServicioClimatizacion {

    private final Ventilador ventilador;

    private final GestorComandos comandos;

    private final PublicadorEventos eventos;

    private EstrategiaClimatizacion estrategia =
            new EstrategiaAhorro();

    public ServicioClimatizacion(
            Ventilador ventilador,
            GestorComandos comandos,
            PublicadorEventos eventos) {

        this.ventilador = Objects.requireNonNull(ventilador);

        this.comandos = Objects.requireNonNull(comandos);

        this.eventos = Objects.requireNonNull(eventos);
    }

    public void cambiarEstrategia(
            EstrategiaClimatizacion nueva) {

        Objects.requireNonNull(nueva);

        if (!estrategia.obtenerNombre()
                .equals(nueva.obtenerNombre())) {

            estrategia = nueva;

            eventos.publicar(
                    "Climatización",
                    "Estrategia seleccionada: "
                            + nueva.obtenerNombre()
            );
        }
    }

    public String obtenerEstrategia() {

        return estrategia.obtenerNombre();
    }

    public void aplicar(double temperatura) {

        if (!Double.isFinite(temperatura)
                || temperatura < -20
                || temperatura > 60) {

            throw new IllegalArgumentException(
                    "La temperatura debe estar entre -20 y 60 °C.");
        }

        if (estrategia.debeEncender(temperatura)) {

            comandos.ejecutarAutomatico(
                    new ComandoEncender(ventilador)
            );

        } else {

            comandos.ejecutarAutomatico(
                    new ComandoApagar(ventilador)
            );
        }

        eventos.publicar(
                "Climatización",
                "Aplicada estrategia "
                        + estrategia.obtenerNombre()
                        + " a " + temperatura + " °C"
        );
    }
}