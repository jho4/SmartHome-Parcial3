package smarthome.patrones.observer;

import java.util.ArrayList;
import java.util.List;

// Observador concreto que guarda los eventos del hogar.
// Conserva únicamente los últimos 100 eventos.
public final class HistorialEventos implements ObservadorEventos {

    private static final int MAXIMO_EVENTOS = 100;

    private final List<EventoHogar> eventos = new ArrayList<>();

    // Recibe el evento publicado por CentroEventos.
    @Override
    public void alRecibirEvento(EventoHogar evento) {

        if (evento == null) {
            throw new IllegalArgumentException("El evento no puede ser nulo.");
        }

        eventos.add(evento);

        // Si superamos el límite, quitamos el más antiguo.
        if (eventos.size() > MAXIMO_EVENTOS) {
            eventos.remove(0);
        }
    }

    // Entrega una copia de los eventos registrados.
    public List<EventoHogar> consultar() {
        return new ArrayList<>(eventos);
    }
}