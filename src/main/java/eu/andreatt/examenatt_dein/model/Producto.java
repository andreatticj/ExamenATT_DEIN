package eu.andreatt.examenatt_dein.model;

import javafx.scene.image.ImageView;
import java.util.Objects;

/**
 * Clase que representa un producto en el sistema.
 */
public class Producto {

    private String codigo, nombre;
    private float precio;
    private int disponible;
    private ImageView imagen;

    /**
     * Constructor de la clase Producto sin imagen.
     *
     * @param codigo     Código del producto.
     * @param nombre     Nombre del producto.
     * @param precio     Precio del producto.
     * @param disponible Cantidad disponible del producto.
     */
    public Producto(String codigo, String nombre, float precio, int disponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = disponible;
    }

    /**
     * Constructor de la clase Producto con imagen.
     *
     * @param codigo     Código del producto.
     * @param nombre     Nombre del producto.
     * @param precio     Precio del producto.
     * @param disponible Cantidad disponible del producto.
     * @param imagen     Imagen asociada al producto.
     */
    public Producto(String codigo, String nombre, float precio, int disponible, ImageView imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = disponible;
        this.imagen = imagen;
    }

    /**
     * Obtiene el código del producto.
     *
     * @return Código del producto.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return Nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombre Nuevo nombre del producto.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del producto.
     *
     * @return Precio del producto.
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     *
     * @param precio Nuevo precio del producto.
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * Obtiene la cantidad disponible del producto.
     *
     * @return Cantidad disponible del producto.
     */
    public int getDisponible() {
        return disponible;
    }

    /**
     * Establece la cantidad disponible del producto.
     *
     * @param disponible Nueva cantidad disponible del producto.
     */
    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    /**
     * Obtiene la imagen asociada al producto.
     *
     * @return Imagen del producto.
     */
    public ImageView getImagen() {
        return imagen;
    }

    /**
     * Establece la imagen asociada al producto.
     *
     * @param imagen Nueva imagen del producto.
     */
    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }

    /**
     * Calcula el valor de hash del objeto Producto.
     *
     * @return Valor de hash basado en los atributos del producto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo, disponible, nombre, precio);
    }

    /**
     * Compara este producto con otro para determinar si son iguales.
     *
     * @param obj Objeto a comparar con este producto.
     * @return true si los productos son iguales; false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Producto other = (Producto) obj;
        return Objects.equals(codigo, other.codigo) && disponible == other.disponible
                && Objects.equals(nombre, other.nombre)
                && Float.floatToIntBits(precio) == Float.floatToIntBits(other.precio);
    }
}
