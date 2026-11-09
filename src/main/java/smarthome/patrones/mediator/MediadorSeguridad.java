package smarthome.patrones.mediator;

import smarthome.modelo.Alarma;

import smarthome.patrones.command.ComandoEncender;
import smarthome.patrones.command.GestorComandos;

import smarthome.patrones.composite.DispositivoControlable;

// Implementación del patrón Mediator.
// Coordina la alarma y la luz de seguridad cuando recibe un movimiento.
// El sensor no se comunica directamente con estos componentes.
public final class MediadorSeguridad implements MediadorHogar {

    private final Alarma alarma;

    private final DispositivoControlable luzSeguridad;

    private final GestorComandos comandos;

    public MediadorSeguridad(
            Alarma alarma,
            DispositivoControlable luzSeguridad,
            GestorComandos comandos) {

        if (alarma == null
                || luzSeguridad == null
                || comandos == null) {

            throw new IllegalArgumentException(
                    "Faltan componentes de seguridad.");
        }

        this.alarma = alarma;
        this.luzSeguridad = luzSeguridad;
        this.comandos = comandos;
    }

    // Recibe las detecciones enviadas por el sensor.
    @Override
    public void recibirMovimiento() {

        // Guardamos si la alarma ya estaba activada.
        boolean estabaActivada = alarma.estaActivada();

        // State decide cómo responder al movimiento.
        alarma.detectarMovimiento();

        // Encendemos la luz solo cuando la alarma acaba de activarse.
        if (!estabaActivada && alarma.estaActivada()) {
            comandos.ejecutarAutomatico(new ComandoEncender(luzSeguridad));
        }
    }
}