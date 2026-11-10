package smarthome.patrones.builder;

import smarthome.modelo.Escena;
import smarthome.patrones.command.Comando;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Builder: permite construir una escena
// paso a paso y validarla al final.
public final class ConstructorEscena {

    private String nombre;

    private final List<Comando> acciones =
            new ArrayList<>();

    public ConstructorEscena conNombre(String nombre) {

        this.nombre = nombre;

        return this;
    }

    public ConstructorEscena agregarComando(Comando comando) {

        acciones.add(Objects.requireNonNull(comando));

        return this;
    }

    public Escena construir() {

        return new Escena(nombre, acciones);
    }
}