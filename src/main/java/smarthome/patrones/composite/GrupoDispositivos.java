package smarthome.patrones.composite;

import java.util.ArrayList;
import java.util.List;

// Se utiliza composite para trata a un grupo como si fuera un dispositivo controlable.

public final class GrupoDispositivos implements DispositivoControlable {

    private final String nombre;
    private final List<DispositivoControlable> elementos;

    public GrupoDispositivos(
            String nombre,
            List<DispositivoControlable> elementos) {

        // Condicional para verificar que el grupo tenga nombre y dispositivos.
        if (nombre == null || nombre.isBlank()
                || elementos == null || elementos.isEmpty()) {

            throw new IllegalArgumentException(
                    "El grupo necesita un nombre y dispositivos.");
        }

        // Condicional para verificar que no contenga elementos nulos.
        for (DispositivoControlable elemento : elementos) {

            if (elemento == null) {
                throw new IllegalArgumentException(
                        "Un grupo no puede contener dispositivos nulos.");
            }
        }

        this.nombre = nombre;
        this.elementos = new ArrayList<>(elementos);

        obtenerIndividuales();
    }

    @Override
    public String obtenerNombre() {
        return nombre;
    }

    @Override
    public void encender() {

        // Ciclo para encender todos los dispositivos del grupo.
        for (DispositivoControlable elemento : elementos) {
            elemento.encender();
        }
    }

    @Override
    public void apagar() {

        // For para aqpagar todos los dispositivos del grupo.
        for (DispositivoControlable elemento : elementos) {
            elemento.apagar();
        }
    }

    @Override
    public boolean estaEncendido() {

        // El grupo se encuentra encendido, cuando todos sus elementos lo están.
        for (DispositivoControlable elemento : elementos) {

            if (!elemento.estaEncendido()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<DispositivoControlable> obtenerIndividuales() {

        List<DispositivoControlable> individuales =
                new ArrayList<>();

        // For para recorrer los elementos del grupo.
        for (DispositivoControlable elemento : elementos) {

            List<DispositivoControlable> actuales =
                    elemento.obtenerIndividuales();

            // For para recorrer los dispositivos individuales.
            for (DispositivoControlable individual : actuales) {

                // Se comprueba si previamente se había agregado el mismo dispositivo.
                for (DispositivoControlable guardado : individuales) {

                    if (guardado == individual) {

                        throw new IllegalArgumentException(
                                "Un dispositivo aparece repetido en el grupo.");
                    }
                }

                individuales.add(individual);
            }
        }

        return List.copyOf(individuales);
    }
}