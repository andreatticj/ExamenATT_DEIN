package eu.andreatt.examenatt_dein.dao;

import eu.andreatt.examenatt_dein.bbdd.ConexionBD;
import eu.andreatt.examenatt_dein.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDao {
    private ConexionBD conexion;

    public ObservableList<Producto> cargarProductos() {
        ObservableList<Producto> productos = FXCollections.observableArrayList();
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT * FROM productos";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                float precio = rs.getFloat("precio");
                int disponible = rs.getInt("disponible");

                productos.add(new Producto(codigo, nombre, precio, disponible));
            }
            rs.close();
            conexion.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public boolean crearProducto(Producto p) {
        try {
            conexion = new ConexionBD();
            String consulta = "INSERT INTO productos (codigo, nombre, precio, disponible) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, p.getCodigo());
            pstmt.setString(2, p.getNombre());
            pstmt.setFloat(3, p.getPrecio());
            pstmt.setInt(4, p.getDisponible());

            pstmt.executeUpdate();
            conexion.closeConnection();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public InputStream dameImagen(String codigo) {
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT imagen FROM productos WHERE codigo = ?";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, codigo);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getBinaryStream("imagen");
            }

            rs.close();
            conexion.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean borrarProducto(Producto p) {
        try {
            conexion = new ConexionBD();
            String consulta = "DELETE FROM productos WHERE codigo = ?";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, p.getCodigo());

            pstmt.executeUpdate();
            conexion.closeConnection();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editarProducto(Producto p) {
        try {
            conexion = new ConexionBD();
            String consulta = "UPDATE productos SET nombre = ?, precio = ?, disponible = ? WHERE codigo = ?";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setString(1, p.getNombre());
            pstmt.setFloat(2, p.getPrecio());
            pstmt.setInt(3, p.getDisponible());
            pstmt.setString(4, p.getCodigo());

            pstmt.executeUpdate();
            conexion.closeConnection();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertarImagen(String ruta, String codigo) {
        try {
            File file = new File(ruta);
            byte[] imageData = Files.readAllBytes(file.toPath());

            conexion = new ConexionBD();
            String consulta = "UPDATE productos SET imagen = ? WHERE codigo = ?";

            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);
            pstmt.setBytes(1, imageData);
            pstmt.setString(2, codigo);

            pstmt.executeUpdate();
            conexion.closeConnection();
            return true;

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeProducto(String codigo) {
        try {
            conexion = new ConexionBD();
            String consulta = "SELECT * FROM productos WHERE codigo = ?";
            PreparedStatement pstmt = conexion.getConexion().prepareStatement(consulta);

            pstmt.setString(1, codigo);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }

            rs.close();
            conexion.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}





