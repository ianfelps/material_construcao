package dao;

import model.TelefoneCliente;
import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefoneClienteDAO {

    // metodo para inserir um telefone
    public void inserir(TelefoneCliente telefone) {
        String sql = "INSERT INTO TB_TELEFONE_CLIENTE (ID_CLIENTE, NR_TELEFONE) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, telefone.getIdCliente());
            stmt.setString(2, telefone.getNumeroTelefone());

            stmt.executeUpdate();
            System.out.println("Telefone do cliente inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir telefone do cliente: " + e.getMessage());
        }
    }

    // metodo para atualizar um telefone
    public void atualizar(TelefoneCliente telefone) {
        String sql = "UPDATE TB_TELEFONE_CLIENTE SET NR_TELEFONE = ? WHERE ID_TELEFONE_CLIENTE = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, telefone.getNumeroTelefone());
            stmt.setInt(2, telefone.getIdTelefoneCliente());

            stmt.executeUpdate();
            System.out.println("Telefone do cliente atualizado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar telefone do cliente: " + e.getMessage());
        }
    }

    // metodo para deletar todos os telefones por cliente
    public void deletarPorIdCliente(int idCliente) {
        String sql = "DELETE FROM TB_TELEFONE_CLIENTE WHERE ID_CLIENTE = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar telefones por ID de Cliente: " + e.getMessage());
        }
    }

    // metodo para listar todos os telefones por cliente
    public List<TelefoneCliente> listarTodosPorCliente(int idCliente) {
        String sql = "SELECT * FROM TB_TELEFONE_CLIENTE WHERE ID_CLIENTE = ?";
        List<TelefoneCliente> telefones = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TelefoneCliente telefoneCliente = new TelefoneCliente();
                    telefoneCliente.setIdTelefoneCliente(rs.getInt("ID_TELEFONE_CLIENTE"));
                    telefoneCliente.setIdCliente(rs.getInt("ID_CLIENTE"));
                    telefoneCliente.setNumeroTelefone(rs.getString("NR_TELEFONE"));
                    telefones.add(telefoneCliente);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar telefones do cliente: " + e.getMessage());
        }

        return telefones;
    }
}
