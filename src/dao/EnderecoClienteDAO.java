package dao;

import model.EnderecoCliente;
import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoClienteDAO {

    // metodo para inserir um endereco
    public void inserir(EnderecoCliente endereco) {
        String sql = "INSERT INTO TB_ENDERECO_CLIENTE (ID_CLIENTE, SG_UF, NO_ENDERECO, NO_CIDADE) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, endereco.getIdCliente());
            stmt.setString(2, endereco.getSiglaUF());
            stmt.setString(3, endereco.getNomeEndereco());
            stmt.setString(4, endereco.getNomeCidade());
            stmt.executeUpdate();

            System.out.println("Endereço inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir endereço: " + e.getMessage());
        }
    }

    // metodo para atualizar um endereco
    public void atualizar(EnderecoCliente endereco) {
        String sql = "UPDATE TB_ENDERECO_CLIENTE SET ID_CLIENTE = ?, SG_UF = ?, NO_ENDERECO = ?, NO_CIDADE = ? WHERE ID_ENDERECO_CLIENTE = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, endereco.getIdCliente());
            stmt.setString(2, endereco.getSiglaUF());
            stmt.setString(3, endereco.getNomeEndereco());
            stmt.setString(4, endereco.getNomeCidade());
            stmt.setInt(5, endereco.getIdEnderecoCliente());
            stmt.executeUpdate();

            System.out.println("Endereço atualizado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar endereço: " + e.getMessage());
        }
    }

    // metodo para deletar todos os enderecos por cliente
    public void deletarPorIdCliente(int idCliente) {
        String sql = "DELETE FROM TB_ENDERECO_CLIENTE WHERE ID_CLIENTE = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar endereços por ID de Cliente: " + e.getMessage());
        }
    }

    // metodo para listar todos os enderecos por cliente
    public List<EnderecoCliente> listarTodosPorCliente(int idCliente) {
        List<EnderecoCliente> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM TB_ENDERECO_CLIENTE WHERE ID_CLIENTE = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    EnderecoCliente endereco = new EnderecoCliente();
                    endereco.setIdEnderecoCliente(rs.getInt("ID_ENDERECO_CLIENTE"));
                    endereco.setIdCliente(rs.getInt("ID_CLIENTE"));
                    endereco.setSiglaUF(rs.getString("SG_UF"));
                    endereco.setNomeEndereco(rs.getString("NO_ENDERECO"));
                    endereco.setNomeCidade(rs.getString("NO_CIDADE"));
                    enderecos.add(endereco);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar endereços por ID de Cliente: " + e.getMessage());
        }

        return enderecos;
    }
}