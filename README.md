# Examen 1 DEIN - Gestión de Productos

Este es un proyecto para gestionar productos en una base de datos, desarrollado utilizando JavaFX para la interfaz gráfica de usuario y conectando a una base de datos SQL para almacenar la información. El proyecto está diseñado para gestionar productos, permitiendo realizar operaciones Crear, Leer, Actualizar y Eliminar.

## Estructura del Proyecto

El proyecto está organizado en varias carpetas y archivos clave:

### 1. **`application/`**
Contiene las clases principales que manejan la lógica de la aplicación:
- **`Main.java`**: Clase principal de la aplicación que inicia la interfaz gráfica.

### 2. **`bbdd/`**
Contiene la clase para gestionar la conexión a la base de datos:
- **`ConexionBD.java`**: Clase que establece la conexión con la base de datos y gestiona las operaciones de consulta y actualización de productos.

### 3. **`controllers/`**
Contiene los controladores para las vistas JavaFX:
- **`ProductoController.java`**: Controlador para gestionar las operaciones sobre los productos en la interfaz gráfica.

### 4. **`dao/`**
Contiene las clases de acceso a datos:
- **`ProductoDao.java`**: Clase que maneja las operaciones CRUD de los productos en la base de datos.

### 5. **`model/`**
Contiene las clases que representan los modelos de datos utilizados en la aplicación:
- **`Producto.java`**: Clase que representa un producto.

### 6. **`utils/`**
Contiene utilidades y configuraciones generales:
- **`Propiedades.java`**: Clase para gestionar las propiedades de configuración de la aplicación.

### 7. **`fxml/`**
Contiene los archivos de diseño de la interfaz gráfica:
- **`Productos.fxml`**: Fichero FXML para la ventana principal de la gestión de productos.

### 8. **`css/`**
Contiene los archivos de estilo:
- **`application.css`**: Archivo CSS que define los estilos para la interfaz gráfica.

### 9. **`images/`**
Contiene imágenes utilizadas en la aplicación:
- **`bollos.png`**
- **`carrito.png`**
- **`chorizo.png`**
- **`jamon.jpeg`**
- **`limones.jpeg`**
- **`naranjas.png`**
- **`platanos.png`**

### 10. **Archivos de configuración**
- **`configuration.properties`**: Archivo de configuración con las propiedades de la aplicación, como la conexión a la base de datos y otros parámetros de la aplicación.

### 11. **`module-info.java`**
Define los módulos de la aplicación para el sistema de módulos de Java.

## Requisitos

- **Java 11 o superior**
- **JavaFX**: Asegúrate de tener JavaFX configurado correctamente en tu entorno de desarrollo.
- **Base de Datos SQL**: El proyecto utiliza una base de datos SQL para almacenar los productos.

## Configuración de la Base de Datos

1. Configura los parámetros de conexión a la base de datos en el archivo `configuration.properties`.

## Ejecución del Proyecto

1. Asegúrate de que JavaFX esté correctamente configurado en tu IDE.
2. Abre la clase `Main.java` y ejecuta la aplicación.
3. La interfaz de usuario te permitirá gestionar los productos.

## Funcionalidades

- **Gestión de Productos**: Permite agregar, modificar y eliminar productos de la base de datos.
- **Visualización de Productos**: Los productos se muestran en una tabla en la interfaz gráfica, con la posibilidad de ver imágenes relacionadas con cada uno.

## Personalización

Puedes modificar los parámetros de configuración de la base de datos en el archivo `configuration.properties` y personalizar la interfaz gráfica modificando los archivos FXML y CSS. Las imágenes de productos también pueden ser cambiadas según las necesidades del proyecto.
