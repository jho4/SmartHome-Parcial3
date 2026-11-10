package smarthome.controller;

import smarthome.modelo.ResumenDispositivo;

import smarthome.patrones.facade.FachadaHogar;
import smarthome.patrones.observer.ObservadorEventos;

import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Objects;

// Controlador MVC:
// recibe las acciones del usuario,
// llama a la fachada y actualiza la interfaz.
public final class ControladorPrincipal {

    @FXML
    private Label estadoSala;

    @FXML
    private Label estadoHabitacion;

    @FXML
    private Label estadoVentilador;

    @FXML
    private Label estadoAlarma;

    @FXML
    private Label modoActual;

    @FXML
    private ComboBox<String> selectorModo;

    @FXML
    private TextField campoTemperatura;

    @FXML
    private TextArea areaEventos;

    @FXML
    private Button botonDeshacer;

    private FachadaHogar fachada;

    private final ObservadorEventos observadorVista =
            evento -> actualizarVista();


    @FXML
    private void initialize() {

        selectorModo.getItems().addAll(
                "Ahorro",
                "Confort"
        );

        selectorModo.setValue("Ahorro");

        campoTemperatura.setText("26");
    }


    public void asignarFachada(FachadaHogar fachada) {

        this.fachada = Objects.requireNonNull(fachada);

        fachada.agregarObservador(observadorVista);

        actualizarVista();
    }


    public void cerrar() {

        if (fachada != null) {

            fachada.quitarObservador(observadorVista);
        }
    }


    private void actualizarVista() {

        for (ResumenDispositivo dispositivo
                : fachada.consultarDispositivos()) {

            Label etiqueta = switch (dispositivo.id()) {

                case "sala" -> estadoSala;

                case "habitacion" -> estadoHabitacion;

                case "ventilador" -> estadoVentilador;

                default -> null;
            };

            if (etiqueta != null) {

                etiqueta.setText(
                        dispositivo.encendido()
                                ? "Encendido"
                                : "Apagado"
                );
            }
        }

        estadoAlarma.setText(
                fachada.consultarEstadoAlarma()
        );

        modoActual.setText(
                fachada.consultarEstrategia()
        );

        botonDeshacer.setDisable(
                !fachada.puedeDeshacer()
        );

        List<String> lineas = fachada.consultarEventos()
                .stream()
                .map(evento -> evento.describir())
                .toList();

        int desde = Math.max(
                0,
                lineas.size() - 25
        );

        areaEventos.setText(
                String.join(
                        "\n",
                        lineas.subList(
                                desde,
                                lineas.size()
                        )
                )
        );

        areaEventos.positionCaret(
                areaEventos.getLength()
        );
    }


    private void realizar(Runnable accion) {

        try {

            accion.run();

            actualizarVista();

        } catch (IllegalArgumentException
                 | IllegalStateException error) {

            Alert aviso = new Alert(
                    Alert.AlertType.WARNING
            );

            aviso.setTitle(
                    "Operación no realizada"
            );

            aviso.setHeaderText(
                    "Revisa la acción solicitada"
            );

            aviso.setContentText(
                    error.getMessage()
            );

            aviso.showAndWait();

            actualizarVista();
        }
    }


    @FXML
    private void encenderSala() {

        realizar(() -> fachada.encender("sala"));
    }


    @FXML
    private void apagarSala() {

        realizar(() -> fachada.apagar("sala"));
    }


    @FXML
    private void encenderHabitacion() {

        realizar(() -> fachada.encender("habitacion"));
    }


    @FXML
    private void apagarHabitacion() {

        realizar(() -> fachada.apagar("habitacion"));
    }


    @FXML
    private void encenderVentilador() {

        realizar(() -> fachada.encender("ventilador"));
    }


    @FXML
    private void apagarVentilador() {

        realizar(() -> fachada.apagar("ventilador"));
    }


    @FXML
    private void encenderLuces() {

        realizar(fachada::encenderLuces);
    }


    @FXML
    private void apagarLuces() {

        realizar(fachada::apagarLuces);
    }


    @FXML
    private void deshacer() {

        realizar(fachada::deshacer);
    }


    @FXML
    private void armarAlarma() {

        realizar(fachada::armarAlarma);
    }


    @FXML
    private void desarmarAlarma() {

        realizar(fachada::desarmarAlarma);
    }


    @FXML
    private void simularMovimiento() {

        realizar(fachada::simularMovimiento);
    }


    @FXML
    private void escenaNoche() {

        realizar(() ->
                fachada.ejecutarEscena("Noche")
        );
    }


    @FXML
    private void escenaLlegada() {

        realizar(() ->
                fachada.ejecutarEscena("Llegada")
        );
    }


    @FXML
    private void aplicarClimatizacion() {

        realizar(() -> {

            double temperatura;

            try {

                temperatura = Double.parseDouble(
                        campoTemperatura
                                .getText()
                                .trim()
                                .replace(',', '.')
                );

            } catch (NumberFormatException error) {

                throw new IllegalArgumentException(
                        "Escribe una temperatura válida.");
            }

            fachada.aplicarClimatizacion(
                    selectorModo.getValue(),
                    temperatura
            );
        });
    }
}