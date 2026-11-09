package smarthome.patrones.observer;

public interface PublicadorEventos {

    void publicar(String origen, String detalle);
}