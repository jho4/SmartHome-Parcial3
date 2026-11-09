package smarthome.modelo;

import smarthome.patrones.observer.PublicadorEventos;

import smarthome.patrones.state.EstadoActivada;
import smarthome.patrones.state.EstadoAlarma;
import smarthome.patrones.state.EstadoDesarmada;

// Representa la alarma del hogar.
// Es el contexto del patrón State: guarda su estado actual
// y delega las operaciones en él. También publica eventos (Observer).
public final class Alarma {

    private final PublicadorEventos eventos;

    private EstadoAlarma estado = new EstadoDesarmada();

    public Alarma(PublicadorEventos eventos) {

        if (eventos == null) {
            throw new IllegalArgumentException(
                    "El publicador de eventos es obligatorio.");
        }

        this.eventos = eventos;
    }

    // Solicita al estado actual que arme la alarma.
    public void armar() {
        estado.armar(this);
    }

    // Solicita al estado actual que desarme la alarma.
    public void desarmar() {
        estado.desarmar(this);
    }

    // Informa al estado actual sobre un movimiento.
    public void detectarMovimiento() {
        estado.detectarMovimiento(this);
    }

    // Consulta el nombre del estado actual.
    public String obtenerEstado() {
        return estado.obtenerNombre();
    }

    // Verifica si actualmente está activada.
    public boolean estaActivada() {
        return estado instanceof EstadoActivada;
    }

    // Cambia el objeto que representa el estado.
    public void cambiarEstado(EstadoAlarma siguiente) {

        if (siguiente == null) {
            throw new IllegalArgumentException(
                    "El siguiente estado es obligatorio.");
        }

        estado = siguiente;

        // Observer notifica el nuevo estado.
        eventos.publicar("Alarma", "Estado: " + estado.obtenerNombre());
    }

    // Publica mensajes adicionales de la alarma.
    public void informar(String mensaje) {
        eventos.publicar("Alarma", mensaje);
    }
}