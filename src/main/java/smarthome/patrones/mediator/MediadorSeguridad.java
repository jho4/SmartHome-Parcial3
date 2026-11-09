package smarthome.patrones.mediator;

import com.smarthome.modelo.Alarma;

import com.smarthome.patrones.command.ComandoEncender;
import com.smarthome.patrones.command.GestorComandos;

import com.smarthome.patrones.composite.DispositivoControlable;

import java.util.Objects;

// Mediator:
// el sensor no conoce directamente
// la alarma ni la luz de seguridad.
public final class MediadorSeguridad implements MediadorHogar {

    private final Alarma alarma;

    private final DispositivoControlable luzSeguridad;

    private final GestorComandos comandos;

    public MediadorSeguridad(
            Alarma alarma,
            DispositivoControlable luzSeguridad,
            GestorComandos comandos) {

        this.alarma = Objects.requireNonNull(alarma);

        this.luzSeguridad = Objects.requireNonNull(luzSeguridad);

        this.comandos = Objects.requireNonNull(comandos);
    }

    @Override
    public void recibirMovimiento() {

        boolean estabaActivada =
                alarma.estaActivada();

        alarma.detectarMovimiento();

        if (!estabaActivada && alarma.estaActivada()) {

            comandos.ejecutarAutomatico(
                    new ComandoEncender(luzSeguridad)
            );
        }
    }
}