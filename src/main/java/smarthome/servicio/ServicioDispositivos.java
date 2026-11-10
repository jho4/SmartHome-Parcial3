package smarthome.servicio;

import smarthome.modelo.Dispositivo;
import smarthome.modelo.ResumenDispositivo;
import smarthome.patrones.composite.DispositivoControlable;

import java.util.ArrayList;
import java.util.List;

// Servicio que se encarga de guardar los dispositivos controlables,
// que los busca por el identificador y consultar sus estados.
// Se utilizan listas y Fors para conservar
// el orden en que fueron registrados.

public final class ServicioDispositivos {

    private final List<Dispositivo> dispositivos =
            new ArrayList<>();

    public ServicioDispositivos(
            List<? extends Dispositivo> dispositivosIniciales) {

        if (dispositivosIniciales == null) {
            throw new IllegalArgumentException(
                    "La lista inicial es obligatoria.");
        }

        // For para recorrer los dispositivos que se recibieron.
        for (Dispositivo dispositivo : dispositivosIniciales) {

            // Condicional para solo aceptar dispositivos que se puedan controlar.
            if (dispositivo == null
                    || !(dispositivo instanceof DispositivoControlable)) {

                throw new IllegalArgumentException(
                        "Se esperaba un dispositivo controlable.");
            }

            // For para verificar que su identificador no esté repetido.
            for (Dispositivo registrado : dispositivos) {

                if (registrado.obtenerId()
                        .equals(dispositivo.obtenerId())) {

                    throw new IllegalArgumentException(
                            "Hay identificadores de dispositivos repetidos.");
                }
            }

            // Si supera las validaciones, se agrega el dispositivo
            dispositivos.add(dispositivo);
        }
    }

    public DispositivoControlable buscar(String id) {

        // For para buscar el dispositivo recorriendo la lista.
        for (Dispositivo dispositivo : dispositivos) {

            if (dispositivo.obtenerId().equals(id)) {

                return (DispositivoControlable) dispositivo;
            }
        }

        // Si se recorre toda, el dispositivo no existe.
        throw new IllegalArgumentException(
                "No existe el dispositivo: " + id);
    }

    public List<ResumenDispositivo> consultar() {

        List<ResumenDispositivo> resumenes =
                new ArrayList<>();

        // For para recorrer todos los dispositivos registrados.
        for (Dispositivo dispositivo : dispositivos) {

            DispositivoControlable controlable =
                    (DispositivoControlable) dispositivo;

            // Genramos un resumen de cada dispositivo.
            resumenes.add(
                    new ResumenDispositivo(
                            dispositivo.obtenerId(),
                            dispositivo.obtenerNombre(),
                            controlable.estaEncendido()
                    )
            );
        }

        return List.copyOf(resumenes);
    }
}