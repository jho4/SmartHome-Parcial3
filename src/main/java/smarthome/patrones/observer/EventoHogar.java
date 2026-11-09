package smarthome.patrones.observer;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public record EventoHogar(
        LocalTime momento,
        String origen,
        String detalle) {

    private static final DateTimeFormatter FORMATO =
            DateTimeFormatter.ofPattern("HH:mm:ss");

    public EventoHogar {

        Objects.requireNonNull(momento);
        Objects.requireNonNull(origen);
        Objects.requireNonNull(detalle);
    }

    public String describir() {

        return momento.format(FORMATO)
                + " - "
                + origen
                + ": "
                + detalle;
    }
}