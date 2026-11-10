package smarthome.patrones.strategy;

public interface EstrategiaClimatizacion {

    String obtenerNombre();

    boolean debeEncender(double temperatura);
}