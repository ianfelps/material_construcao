package dao;

import database.ConnectionFactory;
import model.Administrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdministradorDAO {

    // metodo para verificar o login
    public boolean validarLogin(String login, String senha) {
        String sql = "SELECT * FROM TB_ADMINISTRADOR WHERE NO_LOGIN = ? AND NO_SENHA = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                // Se encontrar um registro, retorna true
                return rs.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao validar login: " + e.getMessage(), e);
        }
    }
}
