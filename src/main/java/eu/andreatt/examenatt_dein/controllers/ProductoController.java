package eu.andreatt.examenatt_dein.controllers;

import eu.andreatt.examenatt_dein.dao.ProductoDao;
import eu.andreatt.examenatt_dein.model.Producto;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

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
    private TableView<Producto> tableProductos;

    @FXML
    private TextField textFieldCodigoProducto;

    @FXML
    private TextField textFieldNombre;

    @FXML
    private TextField textFieldPrecio;

    private ProductoDao productoDao;
    private Producto producto;

    private ObservableList<Producto> productosExistentes;
    private String codigo, nombre;
    private float precio;
    private int disponible;

    String ruta, rutaImagenActual;


    @FXML
    public void initialize() {
        try {
            //Instanciar Dao
            productoDao = new ProductoDao();

            //Cargar productos
            productosExistentes = productoDao.cargarProductos();

            //Instanciar columnas
            columnaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            columnaDisponible.setCellValueFactory(cellData -> {
                Producto p = cellData.getValue();
                Boolean v = (p.getDisponible() == 1);
                return new ReadOnlyBooleanWrapper(v);
            });
            columnaDisponible.setCellFactory(CheckBoxTableCell.<Producto>forTableColumn(columnaDisponible));

            //Cargar tabla
            tableProductos.setItems(productosExistentes);

            //Menu contextual
            ContextMenu contextMenu = new ContextMenu();
            MenuItem imagenContextual = new MenuItem("Ver Imagen");
            MenuItem eliminarContextual = new MenuItem("Eliminar");
            imagenContextual.setOnAction(e -> mostrarImagen());
            eliminarContextual.setOnAction(e -> eliminar());
            contextMenu.getItems().add(imagenContextual);
            contextMenu.getItems().add(eliminarContextual);
            tableProductos.setContextMenu(contextMenu);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void acercaDe(ActionEvent event) {
        generarVentana(Alert.AlertType.INFORMATION, "Gestión de productos 1.0 \nAutor: Andrea Tortosa", "INFO");
    }

    @FXML
    void actionActualizar(ActionEvent event) {
            //Validar errores
            String errores = validarDatos();

            //Mostrar errores existentes
            if(errores.length()>0) {
                generarVentana(Alert.AlertType.ERROR, errores, "ERROR");
            }else {
                //Actualizar producto - con imagen si elige
                try {
                    productoDao.editarProducto(new Producto(textFieldCodigoProducto.getText(), textFieldNombre.getText(), Float.parseFloat(textFieldPrecio.getText()), checkBoxDisponible.isSelected()));
                    if (rutaImagenActual != null) {
                        productoDao.insertarImagen(rutaImagenActual, codigo);
                    }
                    generarVentana(Alert.AlertType.INFORMATION, "Se ha ACTUALIZADO un producto", "INFO");

                    //Refrescar tabla
                    productosExistentes = productoDao.cargarProductos();
                    tableProductos.setItems(productosExistentes);
                } catch (Exception e) {
                    generarVentana(Alert.AlertType.ERROR, e.getMessage(), "ERROR");
                }
                //Vaciar campos
                vaciarCampos();
            }
        }

    @FXML
    void actionClickTabla(MouseEvent event) {
        //Si se ha seleccionado un producto de la tabla
        if(tableProductos.getSelectionModel().getSelectedItem()!=null) {
            try {
                //Cargar campos con información del producto seleccionado
                producto = (Producto) tableProductos.getSelectionModel().getSelectedItem();

                textFieldCodigoProducto.setText(producto.getCodigo());
                textFieldNombre.setText(producto.getNombre());
                textFieldPrecio.setText(String.valueOf(producto.getPrecio()));
                if (producto.getDisponible()==1) {
                    checkBoxDisponible.setSelected(true);
                }else {
                    checkBoxDisponible.setSelected(false);
                }
                //Recuperar imagen si la tiene
                InputStream imagenAlmacenada = productoDao.dameImagen(producto.getCodigo());
                if(imagenAlmacenada!=null) {
                    imageView.setImage(new Image(imagenAlmacenada));
                }else {
                    imageView.setImage(null);
                }

                //Habilitar y Deshabilitar campos necesarios
                textFieldCodigoProducto.setDisable(true);
                botonCrear.setDisable(true);
                botonActualizar.setDisable(false);
            }catch (Exception e) {
                generarVentana(Alert.AlertType.ERROR, e.getMessage(), "ERROR");
            }
        }
    }

    @FXML
    void actionCrear(ActionEvent event) {
        String errores = validarDatos();

        // Si hay errores en los datos, muestra una alerta con los mensajes de error.
        if (!errores.isEmpty()) {
            generarVentana(Alert.AlertType.ERROR, errores, "ERROR");
        }else {
            if(productoDao.existeProducto(codigo)) {
                generarVentana(Alert.AlertType.ERROR, "Ya existe un producto con dicho CÓDIGO", "ERROR");
            }else {
                try {
                    productoDao.crearProducto(new Producto(textFieldCodigoProducto.getText(), textFieldNombre.getText(), Float.parseFloat(textFieldPrecio.getText()), disponible));
                    //Añadir producto - con imagen si elige
                    if(imageView!=null) {
                        productoDao.insertarImagen(ruta, codigo);
                    }
                    generarVentana(Alert.AlertType.INFORMATION, "Se ha CREADO un producto", "INFO");

                    //Refrescar tabla
                    productosExistentes = productoDao.cargarProductos();
                    tableProductos.setItems(productosExistentes);
                } catch (Exception e) {
                    generarVentana(Alert.AlertType.ERROR, e.getMessage(), "ERROR");
                }
                //Vaciar campos
                vaciarCampos();
            }
        }
    }

    @FXML
    void actionLimpiar(ActionEvent event) {
        vaciarCampos();
    }

    @FXML
    void actionSeleccionarImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        File archivoSeleccionado = fileChooser.showOpenDialog(stage);

        if (archivoSeleccionado != null) {
            //Comprobar que la imagen no sea demasiado grande
            try {
                if(Files.size(archivoSeleccionado.toPath())>65000) {
                    //Mensaje de alerta
                    generarVentana(Alert.AlertType.ERROR, "Elige una imagen más pequeña", "ERROR");
                }else {
                    Image image = new Image(archivoSeleccionado.toURI().toString());
                    ruta = archivoSeleccionado.getAbsolutePath();
                    rutaImagenActual = ruta;
                    imageView.setImage(image);
                }
            } catch (IOException e) {

            }
        }
    }

    public void eliminar() {
        try {
            //Borrar producto
            productoDao.borrarProducto(producto);
            generarVentana(Alert.AlertType.INFORMATION, "Se ha BORRADO un producto", "INFO");
            try {
                //Refrescar tabla
                productosExistentes = productoDao.cargarProductos();
                tableProductos.setItems(productosExistentes);
            } catch (Exception e) {
                generarVentana(Alert.AlertType.ERROR, "ERROR al cargar la información", "ERROR");
            }

            //Vaciar campos
            vaciarCampos();
        } catch (Exception e) {
            generarVentana(Alert.AlertType.ERROR, "ERROR al borrar producto", "ERROR");
        }
    }

    private void vaciarCampos() {
            textFieldCodigoProducto.setText("");
            textFieldNombre.setText("");
            textFieldPrecio.setText("");
            checkBoxDisponible.setSelected(false);

            textFieldCodigoProducto.setDisable(false);
            botonCrear.setDisable(false);
            botonActualizar.setDisable(true);
            imageView.setImage(null);
        }

    public void mostrarImagen() {
        // Mostrar imagen si tiene
        if (productoDao.dameImagen(producto.getCodigo()) != null) {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            //Instanciar layout
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER);

            //Añadir imagen
            ImageView imageView = new ImageView(new Image(productoDao.dameImagen(producto.getCodigo())));
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(300);
            imageView.setFitHeight(300);
            vbox.getChildren().add(imageView);

            //Escena
            Scene scene = new Scene(vbox, 300, 300);
            stage.setTitle("PRODUCTO");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } else {
            generarVentana(Alert.AlertType.ERROR, "El producto NO tiene imagen", "ERROR");
        }
    }

    private void generarVentana(Alert.AlertType tipoDeAlerta, String mensaje, String title) {
        Alert alerta = new Alert(tipoDeAlerta);
        alerta.setContentText(mensaje);
        alerta.setHeaderText(null);
        alerta.setTitle(title);
        alerta.showAndWait();
    }

    private String validarDatos() {
        String errores = "";

        // Valida que los campos no estén vacíos y que los valores numéricos sean correctos.
        if (textFieldNombre.getText().isEmpty()) {
            errores+=("* El nombre no puede estar vacío.\n");
        }
        if (textFieldCodigoProducto.getText().isEmpty()) {
            errores+=("* El codigo del producto no puede estar vacía.\n");
        }
        if ( textFieldPrecio.getText().isEmpty()) {
            errores+=("* El precio puede estar vacía.\n");
        }

        return errores;
    }
    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Seleccionar Imágenes");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
    }


}
