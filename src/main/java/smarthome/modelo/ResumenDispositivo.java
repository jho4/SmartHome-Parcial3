package smarthome.modelo;

// Se encarga de guarda la información que se muestra en la interfaz sobre un dispositivo.

public final class ResumenDispositivo {

    private final String id;
    private final String nombre;
    private final boolean encendido;

    public ResumenDispositivo(String id, String nombre, boolean encendido) {
        this.id = id;
        this.nombre = nombre;
        this.encendido = encendido;
    }

    public String id() {
        return id;
    }

    public String nombre() {
        return nombre;
    }

    public boolean encendido() {
        return encendido;
    }
}