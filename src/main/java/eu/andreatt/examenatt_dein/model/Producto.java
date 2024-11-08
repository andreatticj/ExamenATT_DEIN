package eu.andreatt.examenatt_dein.model;

import javafx.scene.image.ImageView;

import java.util.Objects;

public class Producto {

    private String codigo, nombre;
    private float precio;
    private int disponible;
    private ImageView imagen;


    public Producto(String codigo, String nombre, float precio, int disponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = disponible;
    }

    public Producto(String codigo, String nombre, float precio, int disponible, ImageView imagen) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = disponible;
        this.imagen = imagen;
    }


    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public ImageView getImagen() {
        return imagen;
    }

    public void setImagen(ImageView imagen) {
        this.imagen = imagen;
    }


    @Override
    public int hashCode() {
        return Objects.hash(codigo, disponible, nombre, precio);
    }

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
