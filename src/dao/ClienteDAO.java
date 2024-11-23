package dao;

import model.Cliente;
import database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // metodo para inserir um cliente
    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO tb_cliente (no_cliente, nr_rg, nr_cpf, tp_cliente) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCliente());
            stmt.setInt(2, cliente.getRg());
            stmt.setString(3, cliente.getCpf());
            stmt.setString(4, cliente.getTipoCliente());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            e.printStackTrace();
        }
    }

    // metodo para deletar um cliente
    public void deletar(int idCliente) {
        String sql = "DELETE FROM tb_cliente WHERE id_cliente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
        }
        return null;
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
            e.printStackTrace();
        }
        return listaClientes;
    }
}