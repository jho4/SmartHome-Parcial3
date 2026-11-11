# SmartHome - Parcial 3

Aplicación de escritorio para simular el **control y la automatización de un hogar inteligente**, desarrollada con **Java 21, Maven, JavaFX y FXML** para Programación II.

**Integrantes:** Miguel Bocanegra y Jhoan Esteban.

## Funcionalidades

- Encender y apagar las luces de sala y habitación y el ventilador.
- Controlar ambas luces como un grupo y deshacer la última acción manual reversible.
- Aplicar climatización con dos estrategias: **Ahorro** (enciende el ventilador desde 28 °C) y **Confort** (desde 24 °C).
- Activar o desactivar la vigilancia y simular detecciones de movimiento. Si la alarma está **armada**, el movimiento la pasa a **activada** y enciende la luz de sala.
- Ejecutar escenas: **Noche** (apaga luces y ventilador; arma la alarma) y **Llegada** (desarma la alarma y enciende la sala).
- Consultar el historial de eventos del hogar.

> **Terminología:** «Armar» significa activar vigilancia; «desarmar», desactivarla; «activada» indica una alerta por movimiento. La alarma y el sensor son simulados: no hay sirena ni dispositivos físicos.

## Requisitos y ejecución

1. Tener instalado **JDK 21** y **Apache Maven**.
2. Clonar o descargar el repositorio y abrir en una terminal.
3. Ejecutar:

```bash
mvn clean javafx:run
```

Para comprobar la compilación sin abrir la interfaz:

```bash
mvn clean compile
```

La interfaz se carga desde `src/main/resources/smarthome/vista/principal.fxml`. El punto de entrada es `smarthome.app.AplicacionSmartHome`.

## Estructura del código

```text
src/main/
├── java/smarthome/
│   ├── app/             # Inicio y configuración de los componentes
│   ├── controller/      # Controlador de la interfaz (MVC)
│   ├── modelo/          # Luces, ventilador, alarma, sensor y escenas
│   ├── servicio/        # Gestión de dispositivos y climatización
│   └── patrones/
│       ├── observer/    ├── strategy/    ├── command/
│       ├── state/       ├── mediator/    ├── builder/
│       ├── composite/   └── facade/
└── resources/smarthome/vista/principal.fxml
```

**MVC:** `principal.fxml` es la vista; `ControladorPrincipal` recibe las acciones; el modelo y los servicios gestionan el hogar. `FachadaHogar` conecta el controlador con las operaciones del sistema.

## Patrones implementados

Las rutas siguientes corresponden a `src/main/java/smarthome/`.

| Patrón | Ubicación principal | Problema que resuelve y uso real |
|---|---|---|
| **Observer** (Comportamiento) | `patrones/observer/CentroEventos.java`, `HistorialEventos.java`, `ObservadorEventos.java` | Notifica cambios sin acoplar los dispositivos al historial o a la vista. La interfaz y el historial observan eventos. |
| **Strategy** (Comportamiento) | `patrones/strategy/EstrategiaClimatizacion.java`, `EstrategiaAhorro.java`, `EstrategiaConfort.java`; `servicio/ServicioClimatizacion.java` | Permite cambiar la regla de encendido del ventilador sin modificar el servicio. |
| **Command** (Comportamiento) | `patrones/command/Comando.java`, `ComandoEncender.java`, `ComandoApagar.java`, `ComandoArmarAlarma.java`, `ComandoDesarmarAlarma.java`, `GestorComandos.java` | Convierte las operaciones en objetos reutilizables para controles, escenas y automatizaciones; permite deshacer acciones manuales reversibles. |
| **State** (Comportamiento) | `modelo/Alarma.java`; `patrones/state/EstadoAlarma.java`, `EstadoDesarmada.java`, `EstadoArmada.java`, `EstadoActivada.java` | El comportamiento ante el movimiento depende del estado de la alarma: desarmada, armada o activada. |
| **Mediator** (Comportamiento) | `patrones/mediator/MediadorHogar.java`, `MediadorSeguridad.java`; `modelo/SensorMovimiento.java` | Coordina sensor, alarma y luz de sala sin que el sensor conozca directamente estos componentes. |
| **Builder** (creacional, peso **1**) | `patrones/builder/ConstructorEscena.java`; `modelo/Escena.java` | Construye paso a paso las escenas Noche y Llegada a partir de comandos. |
| **Composite** (estructural, peso **3**) | `patrones/composite/DispositivoControlable.java`, `GrupoDispositivos.java` | Trata el grupo de luces como un único dispositivo para encenderlo o apagarlo. |
| **Facade** (estructural, peso **1**) | `patrones/facade/FachadaHogar.java` | Ofrece al controlador una interfaz sencilla para trabajar con los distintos servicios y patrones. |

**Selección adicional:** Builder (1) + Composite (3) + Facade (1) = **5 puntos de complejidad**. Se eligieron porque permiten construir escenas, operar grupos de dispositivos y simplificar la integración entre la vista y el modelo. Se cumple el mínimo de un patrón creacional y uno estructural.

## Comprobación rápida

1. **Command:** enciende la luz de sala y pulsa **Deshacer última acción manual**; debe apagarse.
2. **Composite:** enciende y apaga **todas las luces**; cambian sala y habitación.
3. **Strategy:** aplica **26 °C** con *Ahorro* (ventilador apagado) y luego con *Confort* (encendido).
4. **State + Mediator:** simula movimiento con la alarma desarmada (sin alerta); **activa vigilancia** y simula otra vez (alarma activada y luz de sala encendida). Desactiva la vigilancia antes de continuar.
5. **Builder:** ejecuta **Noche** y luego **Llegada**; verifica los dispositivos y la alarma.
6. **Observer:** revisa el **historial de eventos** durante las acciones anteriores.

## Entrega y colaboración

El repositorio se evidencia aportes de ambos, mediante **commits, Pull Requests y merges**. 
