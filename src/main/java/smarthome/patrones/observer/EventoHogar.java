package smarthome.patrones.observer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

// Guarda los datos de un evento del hogar:
// hora, origen y descripción de lo ocurrido.
public final class EventoHogar {

    private final LocalTime momento;
    private final String origen;
    private final String detalle;

    // Constructor que recibe los datos del evento.
    public EventoHogar(LocalTime momento, String origen, String detalle) {

        if (momento == null
                || origen == null
                || origen.isBlank()
                || detalle == null
                || detalle.isBlank()) {

            throw new IllegalArgumentException(
                    "El evento debe tener hora, origen y detalle.");
        }

        this.momento = momento;
        this.origen = origen;
        this.detalle = detalle;
    }

    public LocalTime momento() {
        return momento;
    }

    public String origen() {
        return origen;
    }

    public String detalle() {
        return detalle;
    }

    // Construye el mensaje que aparecerá en el historial.
    public String describir() {

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm:ss");

        return momento.format(formato) + " - " + origen + ": " + detalle;
    }
}