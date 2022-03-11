
package Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;

public class conexion {

    private conexion() {

    }

    public String URL = "jdbc:mysql://localhost/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static conexion instancia;

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void cerrarresultado(ResultSet resultado) {
        try {
            resultado.close();

        } catch (SQLException error) {
            System.out.println(error);
        }

    }

    public void desconectar(Connection conexion) {
        try {
            conexion.close();

        } catch (SQLException error) {
            System.out.println(error);
        }
    }

    public void cerrarStatement(PreparedStatement statement) {
        try {
            statement.close();

        } catch (SQLException error) {
            System.out.println(error);
        }
    }

    public static conexion getInstance() {
        if (instancia == null) {
            instancia = new conexion();
        }
        return instancia;
    }

}
