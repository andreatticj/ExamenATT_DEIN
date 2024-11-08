package eu.andreatt.examenatt_dein.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;

/**
 * La clase Main extiende de la clase  Application y es el punto de entrada principal
 * para la aplicación JavaFX.
 */
public class Main extends Application {

    /**
     * Inicializa y configura la ventana principal de la aplicación, cargando el archivo FXML
     *
     * @param primaryStage El escenario principal de la aplicación.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            // Logo
            // Se establece el ícono de la ventana principal de la aplicación.
            Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/examenatt_dein/images/carrito.png"));
            primaryStage.getIcons().add(icon);

            // Escena principal
            // Se carga el archivo FXML que define la interfaz de usuario de la aplicación.
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/eu/andreatt/examenatt_dein/fxml/Productos.fxml"));
            Scene scene = new Scene(root, 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/eu/andreatt/examenatt_dein/css/application.css").toExternalForm());

            // Primary Stage
            // Se establece el título, la escena y la propiedad de redimensionado de la ventana principal.
            primaryStage.setTitle("Productos");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * El metodo main es el punto de entrada para lanzar la aplicación.
     *
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
