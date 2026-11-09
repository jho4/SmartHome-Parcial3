package smarthome.patrones.observer;

@FunctionalInterface
public interface ObservadorEventos {

    void alRecibirEvento(EventoHogar evento);
}