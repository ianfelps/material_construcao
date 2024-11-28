package dao;

import model.Cliente;
import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // metodo para inserir um cliente
    public int inserir(Cliente cliente) {
        String sql = "INSERT INTO TB_CLIENTE (NO_CLIENTE, NR_RG, NR_CPF, TP_CLIENTE) VALUES (?, ?, ?, ?)";
        int geradoId = 0;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNomeCliente());
            stmt.setInt(2, cliente.getRg());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getTipoCliente());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    geradoId = generatedKeys.getInt(1);
                    cliente.setIdCliente(geradoId);
                } else {
                    throw new SQLException("Falha ao inserir cliente, nenhum ID obtido.");
                }
            }

            System.out.println("Cliente inserido com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
        }

        return geradoId; // retorna o ID gerado
    }

    // metodo para atualizar um cliente
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE tb_cliente SET no_cliente = ?, nr_rg = ?, nr_cpf = ?, tp_cliente = ? WHERE id_cliente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCliente());
            stmt.setInt(2, cliente.getRg());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getTipoCliente());
            stmt.setInt(5, cliente.getIdCliente());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente: ", e);
        }
    }

    // metodo para deletar um cliente
    public void deletar(int idCliente) {
        String sql = "DELETE FROM TB_CLIENTE WHERE ID_CLIENTE = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
        }
    }

    // metodo para buscar um cliente por ID
    public Cliente buscarPorId(int idCliente) {
        String sql = "SELECT * FROM tb_cliente WHERE id_cliente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNomeCliente(rs.getString("no_cliente"));
                cliente.setRg(rs.getInt("nr_rg"));
                cliente.setCpf(rs.getString("nr_cpf"));
                cliente.setTipoCliente(rs.getString("tp_cliente"));
                return cliente;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar cliente.", e);
        }
        return null;
    }

    // metodo para buscar um cliente por nome
    public Cliente buscarPorNome(String nome) {
        String sql = "SELECT * FROM TB_CLIENTE WHERE NO_CLIENTE = ?";
        Cliente cliente = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("ID_CLIENTE"));
                    cliente.setNomeCliente(rs.getString("NO_CLIENTE"));
                    // Configure outros atributos conforme necessário
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar cliente.", e);
        }

        return cliente;
    }

    // metodo para listar todos os clientes
    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM tb_cliente";
        List<Cliente> listaClientes = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("id_cliente"));
                cliente.setNomeCliente(rs.getString("no_cliente"));
                cliente.setRg(rs.getInt("nr_rg"));
                cliente.setCpf(rs.getString("nr_cpf"));
                cliente.setTipoCliente(rs.getString("tp_cliente"));
                listaClientes.add(cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes: ", e);
        }
        return listaClientes;
    }
}