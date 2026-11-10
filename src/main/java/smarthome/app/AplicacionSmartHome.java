package smarthome.app;

import smarthome.controller.ControladorPrincipal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public final class AplicacionSmartHome extends Application {

    @Override
    public void start(Stage escenario) throws IOException {

        FXMLLoader cargador = new FXMLLoader(
                AplicacionSmartHome.class.getResource(
                        "/com/smarthome/vista/principal.fxml"
                )
        );

        Parent vista = cargador.load();

        ControladorPrincipal controlador =
                cargador.getController();

        controlador.asignarFachada(
                ConfiguracionHogar.crear()
        );

        escenario.setTitle(
                "SmartHome - Parcial 3"
        );

        escenario.setScene(
                new Scene(vista, 850, 790)
        );

        escenario.show();

        escenario.setOnHidden(
                evento -> controlador.cerrar()
        );
    }

    public static void main(String[] argumentos) {

        launch(argumentos);
    }
}