package smarthome.patrones.strategy;

public final class EstrategiaConfort
        implements EstrategiaClimatizacion {

    @Override
    public String obtenerNombre() {

        return "Confort";
    }

    @Override
    public boolean debeEncender(double temperatura) {

        return temperatura >= 24;
    }
}