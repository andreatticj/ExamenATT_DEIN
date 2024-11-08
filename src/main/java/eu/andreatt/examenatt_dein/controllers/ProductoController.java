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


    /**
     * Inicializa el controlador cargando los productos, configurando las columnas de la tabla,
     * creando el menú contextual y estableciendo la configuración inicial de la interfaz.
     */
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

    /**
     * Muestra una ventana con la información sobre la aplicación.
     *
     * @param event El evento que dispara la acción.
     */
    @FXML
    void acercaDe(ActionEvent event) {
        generarVentana(Alert.AlertType.INFORMATION, "Gestión de productos 1.0 \nAutor: Andrea Tortosa", "INFO");
    }

    /**
     * Actualiza la información de un producto con los datos introducidos en los campos.
     * Valida los datos antes de realizar la actualización.
     *
     * @param event El evento que dispara la acción.
     */
    @FXML
    void actionActualizar(ActionEvent event) {
        //Validar errores
        String errores = validarDatos();

        //Mostrar errores existentes en los camos
        if(errores.isEmpty()) {
            generarVentana(Alert.AlertType.ERROR, errores, "ERROR");
        }else {
            //Actualizar producto - con imagen si elige
            try {
                productoDao.editarProducto(new Producto(textFieldCodigoProducto.getText(), textFieldNombre.getText(), Float.parseFloat(textFieldPrecio.getText()), disponible));
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

    /**
     * Acción que se ejecuta al hacer clic en una fila de la tabla.
     * Rellena los campos con la información del producto seleccionado.
     *
     * @param event El evento que dispara la acción.
     */
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

    /**
     * Crea un nuevo producto con los datos introducidos en los campos.
     * Si el producto ya existe, muestra un mensaje de error.
     *
     * @param event El evento que dispara la acción.
     */
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
                    if(ruta != null && !ruta.isEmpty()) {
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


    /**
     Resetea todos los campos a su estado inicial.

     * @param event El evento que dispara la acción.
     */
    @FXML
    void actionLimpiar(ActionEvent event) {
        vaciarCampos();
    }

    /**
     * Abre un selector de archivos para elegir una imagen y la asigna al producto.
     *
     * @param event El evento que dispara la acción.
     */
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

    /**
     * Elimina el producto seleccionado despues de una confirmación por parte del usuario.
     */
    public void eliminar() {
        // Crear una alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Estás seguro de eliminar este producto?");
        alert.setContentText("Una vez eliminado, no podrás recuperarlo.");

        // Mostrar la alerta y esperar la respuesta del usuario
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    productoDao.borrarProducto(producto);
                    // Actualizar la tabla
                    productosExistentes = productoDao.cargarProductos();
                    tableProductos.setItems(productosExistentes);
                    generarVentana(Alert.AlertType.INFORMATION, "Producto eliminado", "INFO");
                } catch (Exception e) {
                    generarVentana(Alert.AlertType.ERROR, e.getMessage(), "ERROR");
                }
            }
        });
    }

    /**
     * Muestra la imagen del producto seleccionado en una nueva ventana.
     */
    public void mostrarImagen() {
        Stage stage = new Stage();
        VBox vbox = new VBox();
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(rutaImagenActual));
        vbox.getChildren().add(imageView);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 300, 300);
        stage.setScene(scene);
        stage.setTitle("Imagen del Producto");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Muestra una ventana con el mensaje proporcionado.
     *
     * @param tipo    El tipo de alerta a mostrar (ERROR, INFORMATION, etc.).
     * @param mensaje El mensaje a mostrar en la ventana.
     * @param titulo  El título de la ventana.
     */
    private void generarVentana(Alert.AlertType tipo, String mensaje, String titulo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Vacía los campos de texto de la interfaz.
     */
    private void vaciarCampos() {
        textFieldCodigoProducto.setText("");
        textFieldNombre.setText("");
        textFieldPrecio.setText("");
        imageView.setImage(null);
        checkBoxDisponible.setSelected(false);
        textFieldCodigoProducto.setDisable(false);
        botonCrear.setDisable(false);
        botonActualizar.setDisable(true);
        ruta = null;
        rutaImagenActual = null;
    }

    /**
     * Valida los datos introducidos en los campos de texto.
     *
     * @return Un string con los errores de validación (si los hay).
     */
    private String validarDatos() {
        String errores = "";
        codigo = textFieldCodigoProducto.getText();
        nombre = textFieldNombre.getText();
        try {
            precio = Float.parseFloat(textFieldPrecio.getText());
        }catch (NumberFormatException e) {
            errores = errores + "El precio debe ser numérico \n";
        }
        if(codigo.isEmpty()) {
            errores = errores + "Introduce un CÓDIGO válido \n";
        }
        if(nombre.isEmpty()) {
            errores = errores + "Introduce un NOMBRE válido \n";
        }
        if(textFieldPrecio.getText().isEmpty()) {
            errores = errores + "Introduce un PRECIO válido \n";
        }

        //Disposición de disponibilidad
        if(checkBoxDisponible.isSelected()) {
            disponible = 1;
        }else {
            disponible = 0;
        }

        return errores;
    }

    /**
     * Configura el tipo de archivos que se pueden seleccionar en el FileChooser.
     *
     * @param fileChooser El FileChooser a configurar.
     */
    private void configureFileChooser(FileChooser fileChooser) {
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);
    }

}