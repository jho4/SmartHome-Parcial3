package smarthome.modelo;

import smarthome.patrones.command.Comando;
import smarthome.patrones.command.GestorComandos;

import java.util.List;
import java.util.Objects;

public final class Escena {

    private final String nombre;

    private final List<Comando> comandos;

    public Escena(
            String nombre,
            List<Comando> comandos) {

        if (nombre == null
                || nombre.isBlank()
                || comandos == null
                || comandos.isEmpty()) {

            throw new IllegalArgumentException(
                    "La escena necesita nombre y acciones.");
        }

        if (comandos.stream().anyMatch(Objects::isNull)) {

            throw new IllegalArgumentException(
                    "Una escena no puede tener comandos nulos.");
        }

        this.nombre = nombre;

        this.comandos = List.copyOf(comandos);
    }

    public String obtenerNombre() {

        return nombre;
    }

    public void ejecutar(GestorComandos gestor) {

        Objects.requireNonNull(gestor);

        // Las escenas no forman parte del historial
        // de deshacer las operaciones manuales.
        gestor.limpiarHistorial();

        for (Comando comando : comandos) {

            gestor.ejecutarAutomatico(comando);
        }
    }
}