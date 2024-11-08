package eu.andreatt.examenatt_dein.bbdd;

import eu.andreatt.examenatt_dein.utils.Propiedades;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

/**
 * Clase para gestionar la conexión con la base de datos utilizando parámetros configurados en el archivo de propiedades.
 */
public class ConexionBD {
    private Connection conexion;

    /**
     * Constructor de la clase ConexionBD.
     * Establece la conexión con la base de datos utilizando la URL, el usuario y la contraseña
     * especificados en el archivo de propiedades.
     *
     * @throws SQLException si ocurre un error al conectar con la base de datos.
     */
    public ConexionBD() throws SQLException {
        String url = Propiedades.getValor("url") + "?serverTimezone=" + TimeZone.getDefault().getID();
        String user = Propiedades.getValor("user");
        String password = Propiedades.getValor("password");
        conexion = DriverManager.getConnection(url, user, password);
        conexion.setAutoCommit(true);
    }

    /**
     * Obtiene la conexión actual con la base de datos.
     *
     * @return Objeto Connection que representa la conexión a la base de datos.
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Cierra la conexión con la base de datos.
     *
     * @throws SQLException si ocurre un error al cerrar la conexión.
     */
    public void closeConnection() throws SQLException {
        conexion.close();
    }
}
