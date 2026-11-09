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

import java.util.ArrayList;
import java.util.List;

// Implementación del patrón Facade.
// Ofrece operaciones sencillas para controlar dispositivos, climatización,
// escenas y seguridad. El controlador JavaFX solo usa esta clase.
public final class FachadaHogar {

    private final ServicioDispositivos dispositivos;
    private final ServicioClimatizacion climatizacion;
    private final GestorComandos comandos;
    private final DispositivoControlable grupoLuces;
    private final Alarma alarma;
    private final SensorMovimiento sensor;

    // Guardamos las escenas en una lista tradicional.
    private final List<Escena> escenas;

    private final CentroEventos centroEventos;
    private final HistorialEventos historial;

    public FachadaHogar(
            ServicioDispositivos dispositivos,
            ServicioClimatizacion climatizacion,
            GestorComandos comandos,
            DispositivoControlable grupoLuces,
            Alarma alarma,
            SensorMovimiento sensor,
            List<Escena> escenas,
            CentroEventos centroEventos,
            HistorialEventos historial) {

        if (dispositivos == null
                || climatizacion == null
                || comandos == null
                || grupoLuces == null
                || alarma == null
                || sensor == null
                || escenas == null
                || centroEventos == null
                || historial == null) {

            throw new IllegalArgumentException(
                    "Faltan datos para crear la fachada del hogar.");
        }

        this.dispositivos = dispositivos;
        this.climatizacion = climatizacion;
        this.comandos = comandos;
        this.grupoLuces = grupoLuces;
        this.alarma = alarma;
        this.sensor = sensor;

        // Copia de la lista recibida.
        this.escenas = new ArrayList<>(escenas);

        this.centroEventos = centroEventos;
        this.historial = historial;
    }

    // Consulta los dispositivos y sus estados.
    public List<ResumenDispositivo> consultarDispositivos() {
        return dispositivos.consultar();
    }

    // Consulta el estado actual de la alarma.
    public String consultarEstadoAlarma() {
        return alarma.obtenerEstado();
    }

    // Consulta el nombre de la estrategia activa.
    public String consultarEstrategia() {
        return climatizacion.obtenerEstrategia();
    }

    // Consulta los eventos almacenados.
    public List<EventoHogar> consultarEventos() {
        return historial.consultar();
    }

    // Registra un observador de eventos.
    public void agregarObservador(ObservadorEventos observador) {
        centroEventos.agregarObservador(observador);
    }

    // Retira un observador registrado.
    public void quitarObservador(ObservadorEventos observador) {
        centroEventos.quitarObservador(observador);
    }

    // Enciende un dispositivo mediante Command.
    public void encender(String id) {
        comandos.ejecutarManual(new ComandoEncender(dispositivos.buscar(id)));
    }

    // Apaga un dispositivo mediante Command.
    public void apagar(String id) {
        comandos.ejecutarManual(new ComandoApagar(dispositivos.buscar(id)));
    }

    // Enciende el grupo de luces mediante Composite.
    public void encenderLuces() {
        comandos.ejecutarAutomatico(new ComandoEncender(grupoLuces));
    }

    // Apaga el grupo de luces mediante Composite.
    public void apagarLuces() {
        comandos.ejecutarAutomatico(new ComandoApagar(grupoLuces));
    }

    // Comprueba si existe una acción manual para deshacer.
    public boolean puedeDeshacer() {
        return comandos.puedeDeshacer();
    }

    // Restaura la última acción manual reversible.
    public void deshacer() {
        comandos.deshacerUltimo();
    }

    // Selecciona y aplica una estrategia de climatización.
    public void aplicarClimatizacion(String nombreEstrategia, double temperatura) {

        if (!Double.isFinite(temperatura)
                || temperatura < -20
                || temperatura > 60) {

            throw new IllegalArgumentException(
                    "La temperatura debe estar entre -20 y 60 °C.");
        }

        // Elegimos una implementación del patrón Strategy.
        if ("Ahorro".equals(nombreEstrategia)) {
            climatizacion.cambiarEstrategia(new EstrategiaAhorro());

        } else if ("Confort".equals(nombreEstrategia)) {
            climatizacion.cambiarEstrategia(new EstrategiaConfort());

        } else {
            throw new IllegalArgumentException("Estrategia desconocida.");
        }

        // Aplicamos la estrategia al ventilador.
        climatizacion.aplicar(temperatura);
    }

    // Arma la alarma mediante Command y State.
    public void armarAlarma() {
        comandos.ejecutarAutomatico(new ComandoArmarAlarma(alarma));
    }

    // Desarma la alarma mediante Command y State.
    public void desarmarAlarma() {
        comandos.ejecutarAutomatico(new ComandoDesarmarAlarma(alarma));
    }

    // Solicita al sensor simular una detección.
    public void simularMovimiento() {

        // Las operaciones automáticas invalidan el historial manual.
        comandos.limpiarHistorial();

        sensor.simularMovimiento();
    }

    // Busca y ejecuta una escena del hogar.
    public void ejecutarEscena(String nombre) {

        Escena seleccionada = null;

        // Recorremos las escenas para buscar la solicitada.
        for (Escena escena : escenas) {
            if (escena.obtenerNombre().equals(nombre)) {
                seleccionada = escena;
                break;
            }
        }

        // Validamos que la escena exista.
        if (seleccionada == null) {
            throw new IllegalArgumentException(
                    "La escena no existe: " + nombre);
        }

        // No permitimos ejecutar Noche con la alarma activada.
        if ("Noche".equals(nombre) && alarma.estaActivada()) {
            throw new IllegalStateException(
                    "Desarma la alarma antes de ejecutar Noche.");
        }

        // Ejecutamos los comandos construidos mediante Builder.
        seleccionada.ejecutar(comandos);

        // Informamos mediante Observer.
        centroEventos.publicar("Escena", "Ejecutada: " + nombre);
    }
}