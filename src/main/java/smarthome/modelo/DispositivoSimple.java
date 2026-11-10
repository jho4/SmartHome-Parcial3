package smarthome.modelo;

import smarthome.patrones.composite.DispositivoControlable;
import smarthome.patrones.observer.PublicadorEventos;

import java.util.List;

// Acopla el comportamiento compartido por las luces y los ventiladores.
// Prende, apaga y avisa cuando cambia su estado - Observer.

public abstract class DispositivoSimple extends Dispositivo
        implements DispositivoControlable {

    private final PublicadorEventos eventos;
    private boolean encendido;

    protected DispositivoSimple(
            String id,
            String nombre,
            PublicadorEventos eventos) {

        super(id, nombre);

        if (eventos == null) {
            throw new IllegalArgumentException(
                    "El publicador de eventos es obligatorio.");
        }

        this.eventos = eventos;
    }

    @Override
    public void encender() {
        cambiarEncendido(true);
    }

    @Override
    public void apagar() {
        cambiarEncendido(false);
    }

    private void cambiarEncendido(boolean nuevoEstado) {

        // Evita estados-eventos duplicados o ya presentes cuando el dispositivo ya tiene ese estado.
        if (encendido != nuevoEstado) {

            encendido = nuevoEstado;

            if (encendido) {
                eventos.publicar(obtenerNombre(), "Encendido");
            } else {
                eventos.publicar(obtenerNombre(), "Apagado");
            }
        }
    }

    @Override
    public boolean estaEncendido() {
        return encendido;
    }

    @Override
    public List<DispositivoControlable> obtenerIndividuales() {

        return List.of(this);
    }
}