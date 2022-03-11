package Datos;

import Domain.Productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class vproducto {

    conexion instanciaMysql = conexion.getInstance();
    private Connection conexionTransaccional;
    
    private static final String SQL_SELECT = "select * from productos";
    private static final String SQL_INSERT = "insert into productos (Nombre,Precio,Existencias) values (?,?,?)";
    private static final String SQL_UPDATE = "update productos set Nombre = ?,Precio= ?,Existencias = ? where Id_Producto = ?";
    private static final String SQL_DELETE = "delete from productos where Id_Producto = ?";

    public vproducto(){
        
        
    }
    
    public vproducto(Connection conexionTransaccional){
        this.conexionTransaccional = conexionTransaccional;
        
    }
    
    
    public List<Productos> listar() {
        Connection conexion = null;
        PreparedStatement consultaPreparada = null;
        ResultSet resultado = null;
        List<Productos> producto = new ArrayList<>();
        Productos productos;

        try {/*
            conexion = instanciaMysql.conectar(); conexion normal*/
            conexion = this.conexionTransaccional != null ? this.conexionTransaccional : instanciaMysql.conectar();
            consultaPreparada = conexion.prepareStatement(SQL_SELECT);
            resultado = consultaPreparada.executeQuery();

            while (resultado.next()) {
                int Id_Producto = resultado.getInt("Id_Producto");
                String Nombre = resultado.getString("Nombre");
                double Precio = resultado.getDouble("Precio");
                int Existencias = resultado.getInt("Existencias");

                productos = new Productos(Id_Producto, Nombre, Precio, Existencias);
                producto.add(productos);

            }
        } catch (SQLException error) {
            System.out.println(error);
        } finally {
            instanciaMysql.cerrarresultado(resultado);
            instanciaMysql.cerrarStatement(consultaPreparada);
            //instanciaMysql.desconectar(conexion);
            
            if(this.conexionTransaccional == null){
                instanciaMysql.desconectar(conexion);
            }
        }
        return producto;

    }

    public int insertar(Productos productos) {
        Connection conexion = null;
        PreparedStatement consultaPreparada = null;

        int registros = 0;

        try {
            conexion = this.conexionTransaccional != null ? this.conexionTransaccional : instanciaMysql.conectar();
            consultaPreparada = conexion.prepareStatement(SQL_INSERT);

            consultaPreparada.setString(1, productos.getNombre());
            consultaPreparada.setDouble(2, productos.getPrecio());
            consultaPreparada.setInt(3, productos.getExistencias());
            registros = consultaPreparada.executeUpdate();

        } catch (SQLException error) {
            System.out.println(error);

        } finally {
            instanciaMysql.cerrarStatement(consultaPreparada);
            //instanciaMysql.desconectar(conexion);
             if(this.conexionTransaccional == null){
                instanciaMysql.desconectar(conexion);
            }
        }
        return registros;

    }

    public int modificar(Productos productos) {
        Connection conexion = null;
        PreparedStatement consultaPreparada = null;

        int registros = 0;

        try {
            conexion = this.conexionTransaccional != null ? this.conexionTransaccional : instanciaMysql.conectar();
            consultaPreparada = conexion.prepareStatement(SQL_UPDATE);

            consultaPreparada.setString(1, productos.getNombre());
            consultaPreparada.setDouble(2, productos.getPrecio());
            consultaPreparada.setInt(3, productos.getExistencias());
            consultaPreparada.setInt(4, productos.getId_Producto());

            registros = consultaPreparada.executeUpdate();

        } catch (SQLException error) {
            System.out.println(error);

        } finally {
            instanciaMysql.cerrarStatement(consultaPreparada);
            //instanciaMysql.desconectar(conexion);
             if(this.conexionTransaccional == null){
                instanciaMysql.desconectar(conexion);
            }
        }
        return registros;

    }

    public int eliminar(Productos productos) {

        Connection conexion = null;
        PreparedStatement consultaPreparada = null;

        int registros = 0;

        try {
            conexion = this.conexionTransaccional != null ? this.conexionTransaccional : instanciaMysql.conectar();
            consultaPreparada = conexion.prepareStatement(SQL_DELETE);

            consultaPreparada.setInt(1, productos.getId_Producto());
            registros = consultaPreparada.executeUpdate();

        } catch (SQLException error) {
            System.out.println(error);

        } finally {
            instanciaMysql.cerrarStatement(consultaPreparada);
            //instanciaMysql.desconectar(conexion);
            if(this.conexionTransaccional == null){
                instanciaMysql.desconectar(conexion);
            }
        }
        
        return registros;

    }

}
