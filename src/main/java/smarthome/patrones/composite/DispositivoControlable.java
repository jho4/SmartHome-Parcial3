package smarthome.patrones.composite;

import java.util.List;

public interface DispositivoControlable {

    String obtenerNombre();

    void encender();

    void apagar();

    boolean estaEncendido();

    // Nos permite poder realizar la consulta de los dispositivos individuales
    List<DispositivoControlable> obtenerIndividuales();
}