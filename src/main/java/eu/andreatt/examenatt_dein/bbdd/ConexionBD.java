package eu.andreatt.examenatt_dein.bbdd;

import eu.andreatt.examenatt_dein.utils.Propiedades;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class ConexionBD {
    private Connection conexion;

    public ConexionBD() throws SQLException {
        String url = Propiedades.getValor("url") + "?serverTimezone=" + TimeZone.getDefault().getID();
        String user = Propiedades.getValor("user");
        String password = Propiedades.getValor("password");
        conexion = DriverManager.getConnection(url, user, password);
        conexion.setAutoCommit(true);
    }
    public Connection getConexion() {
        return conexion;
    }
    public void closeConnection() throws SQLException {
        conexion.close();
    }
}