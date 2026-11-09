package smarthome.patrones.observer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// Publicador del patrón Observer.
// Guarda los observadores registrados y les comunica los eventos.
public final class CentroEventos implements PublicadorEventos {

    private final List<ObservadorEventos> observadores = new ArrayList<>();

    // Permite registrar un observador.
    public void agregarObservador(ObservadorEventos observador) {

        if (observador == null) {
            throw new IllegalArgumentException(
                    "El observador no puede ser nulo.");
        }

        // Evitamos registrar el mismo objeto dos veces.
        for (ObservadorEventos registrado : observadores) {
            if (registrado == observador) {
                return;
            }
        }

        observadores.add(observador);
    }

    // Permite retirar un observador.
    public void quitarObservador(ObservadorEventos observador) {
        observadores.remove(observador);
    }

    // Crea un evento y notifica a los observadores.
    @Override
    public void publicar(String origen, String detalle) {

        EventoHogar evento = new EventoHogar(LocalTime.now(), origen, detalle);

        // Recorremos una copia por si un observador se retira durante el aviso.
        List<ObservadorEventos> copia = new ArrayList<>(observadores);

        for (ObservadorEventos observador : copia) {
            observador.alRecibirEvento(evento);
        }
    }
}