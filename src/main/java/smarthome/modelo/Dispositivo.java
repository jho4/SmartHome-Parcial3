package smarthome.modelo;

public abstract class Dispositivo {

    private final String id;
    private final String nombre;

    protected Dispositivo(String id, String nombre) {

        if (id == null || id.isBlank()
                || nombre == null || nombre.isBlank()) {

            throw new IllegalArgumentException(
                    "El dispositivo requiere del identificador y su nombre.");
        }

        this.id = id;
        this.nombre = nombre;
    }

    public String obtenerId() {
        return id;
    }

    public String obtenerNombre() {
        return nombre;
    }
}