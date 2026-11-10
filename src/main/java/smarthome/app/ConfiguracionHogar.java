package smarthome.app;

import smarthome.modelo.Alarma;
import smarthome.modelo.Escena;
import smarthome.modelo.Luz;
import smarthome.modelo.SensorMovimiento;
import smarthome.modelo.Ventilador;

import smarthome.patrones.builder.ConstructorEscena;

import smarthome.patrones.command.ComandoApagar;
import smarthome.patrones.command.ComandoArmarAlarma;
import smarthome.patrones.command.ComandoDesarmarAlarma;
import smarthome.patrones.command.ComandoEncender;
import smarthome.patrones.command.GestorComandos;

import smarthome.patrones.composite.DispositivoControlable;
import smarthome.patrones.composite.GrupoDispositivos;

import smarthome.patrones.facade.FachadaHogar;

import smarthome.patrones.mediator.MediadorSeguridad;

import smarthome.patrones.observer.CentroEventos;
import smarthome.patrones.observer.HistorialEventos;

import smarthome.servicio.ServicioClimatizacion;
import smarthome.servicio.ServicioDispositivos;

import java.util.List;
import java.util.Map;

// Crea los componentes iniciales del hogar
// y establece sus relaciones.
public final class ConfiguracionHogar {

    private ConfiguracionHogar() {
    }

    public static FachadaHogar crear() {

        // 1. Configurar Observer.

        CentroEventos eventos = new CentroEventos();

        HistorialEventos historial = new HistorialEventos();

        eventos.agregarObservador(historial);


        // 2. Crear los dispositivos.

        Luz sala = new Luz(
                "sala",
                "Luz de sala",
                eventos
        );

        Luz habitacion = new Luz(
                "habitacion",
                "Luz de habitación",
                eventos
        );

        Ventilador ventilador = new Ventilador(
                "ventilador",
                "Ventilador",
                eventos
        );


        // 3. Crear el servicio de dispositivos.

        ServicioDispositivos dispositivos =
                new ServicioDispositivos(
                        List.of(sala, habitacion, ventilador)
                );


        // 4. Configurar Composite.

        DispositivoControlable luces =
                new GrupoDispositivos(
                        "Todas las luces",
                        List.of(sala, habitacion)
                );


        // 5. Crear el gestor de comandos.

        GestorComandos comandos = new GestorComandos();


        // 6. Configurar climatización.

        ServicioClimatizacion climatizacion =
                new ServicioClimatizacion(
                        ventilador,
                        comandos,
                        eventos
                );


        // 7. Crear alarma y mediador.

        Alarma alarma = new Alarma(eventos);

        MediadorSeguridad mediador =
                new MediadorSeguridad(
                        alarma,
                        sala,
                        comandos
                );


        // 8. Crear sensor de movimiento.

        SensorMovimiento sensor =
                new SensorMovimiento(
                        "sensor",
                        "Sensor de sala",
                        mediador,
                        eventos
                );


        // 9. Construir escena Noche.

        Escena noche = new ConstructorEscena()
                .conNombre("Noche")
                .agregarComando(
                        new ComandoApagar(luces)
                )
                .agregarComando(
                        new ComandoApagar(ventilador)
                )
                .agregarComando(
                        new ComandoArmarAlarma(alarma)
                )
                .construir();


        // 10. Construir escena Llegada.

        Escena llegada = new ConstructorEscena()
                .conNombre("Llegada")
                .agregarComando(
                        new ComandoDesarmarAlarma(alarma)
                )
                .agregarComando(
                        new ComandoEncender(sala)
                )
                .construir();


        // 11. Construir la fachada del sistema.

        return new FachadaHogar(
                dispositivos,
                climatizacion,
                comandos,
                luces,
                alarma,
                sensor,
                Map.of(
                        "Noche", noche,
                        "Llegada", llegada
                ),
                eventos,
                historial
        );
    }
}