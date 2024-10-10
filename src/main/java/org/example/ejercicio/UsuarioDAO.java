package org.example.ejercicio;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class UsuarioDAO {
    private Connection conexion;

    public void conectar() throws ClassNotFoundException, SQLException, IOException {
        Properties configuration = new Properties();
        configuration.load(R.getProperties("database.properties"));
        String host = configuration.getProperty("host");
        String port = configuration.getProperty("port");
        String name = configuration.getProperty("name");
        String username = configuration.getProperty("username");
        String password = configuration.getProperty("password");

        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + name + "?serverTimezone=UTC",
                username, password);
    }

    public void desconectar() throws SQLException {
        conexion.close();
    }

    public void registrarUsuario(String usuario, String contraseña) throws SQLException {
        String sql = "INSERT INTO usuarios (usuario, contraseña) VALUES (?, ?)";

        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, usuario);
        sentencia.setString(2, contraseña);
        sentencia.executeUpdate();
    }

    public boolean existeUsuario(String usuario) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, usuario);
        ResultSet resultado = sentencia.executeQuery();

        return resultado.next();
    }

    public boolean iniciarSesion(String usuario, String contraseña) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setString(1, usuario);
        sentencia.setString(2, contraseña);
        ResultSet resultado = sentencia.executeQuery();

        return resultado.next();
    }
}
