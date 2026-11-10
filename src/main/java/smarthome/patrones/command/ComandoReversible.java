package smarthome.patrones.command;

// Interfaz - contrato para los comandos manuales
// que permiten recuperar el estado anterior.

public interface ComandoReversible extends Comando {

    void deshacer();

    boolean produjoCambio();
}