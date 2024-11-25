package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    // atributos necessarios para conectar com o banco
    private static final String URL = "jdbc:mysql://localhost:3306/db_material_construcao";
    private static final String USER = "root";
    private static final String PASSWORD = "0905";

    // metodo para conectar com o banco
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC não encontrado.", e);
        }
    }
}