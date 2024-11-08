package eu.andreatt.examenatt_dein.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            //Logo
            Image icon = new Image(getClass().getResourceAsStream("/eu/andreatt/examenatt_dein/images/carrito.png"));
            primaryStage.getIcons().add(icon);

            //Escena principal
            GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("/eu/andreatt/examenatt_dein/fxml/Productos.fxml"));
            Scene scene = new Scene(root,1000,700);
            scene.getStylesheets().add(getClass().getResource("/eu/andreatt/examenatt_dein/css/application.css").toExternalForm());

            //Primary Stage
            primaryStage.setTitle("Productos");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}