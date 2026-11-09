package smarthome.patrones.facade;

import smarthome.modelo.Alarma;
import smarthome.modelo.Escena;
import smarthome.modelo.ResumenDispositivo;
import smarthome.modelo.SensorMovimiento;

import smarthome.patrones.command.ComandoApagar;
import smarthome.patrones.command.ComandoArmarAlarma;
import smarthome.patrones.command.ComandoDesarmarAlarma;
import smarthome.patrones.command.ComandoEncender;
import smarthome.patrones.command.GestorComandos;

import smarthome.patrones.composite.DispositivoControlable;

import smarthome.patrones.observer.CentroEventos;
import smarthome.patrones.observer.EventoHogar;
import smarthome.patrones.observer.HistorialEventos;
import smarthome.patrones.observer.ObservadorEventos;

import smarthome.patrones.strategy.EstrategiaAhorro;
import smarthome.patrones.strategy.EstrategiaConfort;

import smarthome.servicio.ServicioClimatizacion;
import smarthome.servicio.ServicioDispositivos;

import java.util.List;
import java.util.Map;
import java.util.Objects;

// Facade:
// ofrece operaciones sencillas al controlador JavaFX.
public final class FachadaHogar {

    private final ServicioDispositivos dispositivos;

    private final ServicioClimatizacion climatizacion;

    private final GestorComandos comandos;

    private final DispositivoControlable grupoLuces;

    private final Alarma alarma;

    private final SensorMovimiento sensor;

    private final Map<String, Escena> escenas;

    private final CentroEventos centroEventos;

    private final HistorialEventos historial;

    public FachadaHogar(
            ServicioDispositivos dispositivos,
            ServicioClimatizacion climatizacion,
            GestorComandos comandos,
            DispositivoControlable grupoLuces,
            Alarma alarma,
            SensorMovimiento sensor,
            Map<String, Escena> escenas,
            CentroEventos centroEventos,
            HistorialEventos historial) {

        this.dispositivos = Objects.requireNonNull(dispositivos);

        this.climatizacion = Objects.requireNonNull(climatizacion);

        this.comandos = Objects.requireNonNull(comandos);

        this.grupoLuces = Objects.requireNonNull(grupoLuces);

        this.alarma = Objects.requireNonNull(alarma);

        this.sensor = Objects.requireNonNull(sensor);

        this.escenas = Map.copyOf(escenas);

        this.centroEventos = Objects.requireNonNull(centroEventos);

        this.historial = Objects.requireNonNull(historial);
    }

    public List<ResumenDispositivo> consultarDispositivos() {

        return dispositivos.consultar();
    }

    public String consultarEstadoAlarma() {

        return alarma.obtenerEstado();
    }

    public String consultarEstrategia() {

        return climatizacion.obtenerEstrategia();
    }

    public List<EventoHogar> consultarEventos() {

        return historial.consultar();
    }

    public void agregarObservador(ObservadorEventos observador) {

        centroEventos.agregarObservador(observador);
    }

    public void quitarObservador(ObservadorEventos observador) {

        centroEventos.quitarObservador(observador);
    }

    public void encender(String id) {

        comandos.ejecutarManual(
                new ComandoEncender(dispositivos.buscar(id))
        );
    }

    public void apagar(String id) {

        comandos.ejecutarManual(
                new ComandoApagar(dispositivos.buscar(id))
        );
    }

    public void encenderLuces() {

        comandos.ejecutarAutomatico(
                new ComandoEncender(grupoLuces)
        );
    }

    public void apagarLuces() {

        comandos.ejecutarAutomatico(
                new ComandoApagar(grupoLuces)
        );
    }

    public boolean puedeDeshacer() {

        return comandos.puedeDeshacer();
    }

    public void deshacer() {

        comandos.deshacerUltimo();
    }

    public void aplicarClimatizacion(
            String nombreEstrategia,
            double temperatura) {

        if (!Double.isFinite(temperatura)
                || temperatura < -20
                || temperatura > 60) {

            throw new IllegalArgumentException(
                    "La temperatura debe estar entre -20 y 60 °C.");
        }

        if ("Ahorro".equals(nombreEstrategia)) {

            climatizacion.cambiarEstrategia(
                    new EstrategiaAhorro()
            );

        } else if ("Confort".equals(nombreEstrategia)) {

            climatizacion.cambiarEstrategia(
                    new EstrategiaConfort()
            );

        } else {

            throw new IllegalArgumentException(
                    "Estrategia desconocida.");
        }

        climatizacion.aplicar(temperatura);
    }

    public void armarAlarma() {

        comandos.ejecutarAutomatico(
                new ComandoArmarAlarma(alarma)
        );
    }

    public void desarmarAlarma() {

        comandos.ejecutarAutomatico(
                new ComandoDesarmarAlarma(alarma)
        );
    }

    public void simularMovimiento() {

        comandos.limpiarHistorial();

        sensor.simularMovimiento();
    }

    public void ejecutarEscena(String nombre) {

        Escena escena = escenas.get(nombre);

        if (escena == null) {

            throw new IllegalArgumentException(
                    "La escena no existe: " + nombre);
        }

        // La escena Noche se valida antes de modificar dispositivos.
        // No puede ejecutarse si la alarma ya está activada.

        if ("Noche".equals(nombre) && alarma.estaActivada()) {

            throw new IllegalStateException(
                    "Desarma la alarma antes de ejecutar Noche.");
        }

        escena.ejecutar(comandos);

        centroEventos.publicar(
                "Escena",
                "Ejecutada: " + nombre
        );
    }
}