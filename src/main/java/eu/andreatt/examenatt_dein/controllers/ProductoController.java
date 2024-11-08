package eu.andreatt.examenatt_dein.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ProductoController {

    @FXML
    private Button botonActualizar;

    @FXML
    private Button botonCrear;

    @FXML
    private Button botonLimpiar;

    @FXML
    private Button botonSeleccionImagen;

    @FXML
    private CheckBox checkBoxDisponible;

    @FXML
    private TableColumn<P, ?> columnaCodigo;

    @FXML
    private TableColumn<?, ?> columnaDisponible;

    @FXML
    private TableColumn<?, ?> columnaNombre;

    @FXML
    private TableColumn<?, ?> columnaPrecio;

    @FXML
    private ImageView imageView;

    @FXML
    private TableView<?> tableProductos;

    @FXML
    private TextField textFieldCodigoProducto;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private TextField textFieldPrecio;

    @FXML
    void acercaDe(ActionEvent event) {

    }

    @FXML
    void actionActualizar(ActionEvent event) {

    }

    @FXML
    void actionClickTabla(MouseEvent event) {

    }

    @FXML
    void actionCrear(ActionEvent event) {

    }

    @FXML
    void actionLimpiar(ActionEvent event) {

    }

    @FXML
    void actionSeleccionarImagen(ActionEvent event) {

    }

}
