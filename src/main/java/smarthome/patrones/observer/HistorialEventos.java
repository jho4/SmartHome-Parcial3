package smarthome.patrones.observer;

import java.util.ArrayList;
import java.util.List;

// Observador concreto:
// conserva los últimos eventos del hogar.
public final class HistorialEventos implements ObservadorEventos {

    private static final int MAXIMO_EVENTOS = 100;

    private final List<EventoHogar> eventos =
            new ArrayList<>();

    @Override
    public void alRecibirEvento(EventoHogar evento) {

        eventos.add(evento);

        if (eventos.size() > MAXIMO_EVENTOS) {

            eventos.remove(0);
        }
    }

    public List<EventoHogar> consultar() {

        return List.copyOf(eventos);
    }
}