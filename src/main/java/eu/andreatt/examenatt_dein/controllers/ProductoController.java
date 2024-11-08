package eu.andreatt.examenatt_dein.controllers;

import eu.andreatt.examenatt_dein.model.Producto;
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
    private TableColumn<Producto, String> columnaCodigo;

    @FXML
    private TableColumn<Producto, Boolean> columnaDisponible;

    @FXML
    private TableColumn<Producto, String> columnaNombre;

    @FXML
    private TableColumn<Producto, Float> columnaPrecio;

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
