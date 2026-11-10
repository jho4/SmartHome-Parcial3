package smarthome.patrones.strategy;

public final class EstrategiaAhorro
        implements EstrategiaClimatizacion {

    @Override
    public String obtenerNombre() {

        return "Ahorro";
    }

    @Override
    public boolean debeEncender(double temperatura) {

        return temperatura >= 28;
    }
}