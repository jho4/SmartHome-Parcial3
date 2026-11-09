package smarthome.patrones.observer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Sujeto de Observer:
// comunica los eventos a los observadores registrados.
public final class CentroEventos implements PublicadorEventos {

    private final List<ObservadorEventos> observadores =
            new ArrayList<>();

    public void agregarObservador(
            ObservadorEventos observador) {

        Objects.requireNonNull(observador);

        if (!observadores.contains(observador)) {

            observadores.add(observador);
        }
    }

    public void quitarObservador(
            ObservadorEventos observador) {

        observadores.remove(observador);
    }

    @Override
    public void publicar(String origen, String detalle) {

        EventoHogar evento = new EventoHogar(
                LocalTime.now(),
                origen,
                detalle
        );

        for (ObservadorEventos observador
                : List.copyOf(observadores)) {

            observador.alRecibirEvento(evento);
        }
    }
}