package dao;

import database.ConnectionFactory;
import model.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    // metodo para adicionar uma nova venda
    public void inserir(Venda venda) {
        String sql = "INSERT INTO TB_VENDA (ID_CLIENTE, DH_PAGAMENTO, VL_TOTAL_VENDA, IC_PAGO) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, venda.getIdCliente());
            stmt.setTimestamp(2, venda.getDataHoraPagamento() != null ? Timestamp.valueOf(venda.getDataHoraPagamento()) : null);
            stmt.setDouble(3, venda.getValorTotalVenda());
            stmt.setBoolean(4, venda.isStatusPago());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    venda.setIdVenda(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar venda.", e);
        }
    }

    // metodo para atualizar uma venda
    public void atualizar(Venda venda) {
        String sql = "UPDATE TB_VENDA SET ID_CLIENTE = ?, DH_PAGAMENTO = ?, VL_TOTAL_VENDA = ?, IC_PAGO = ? WHERE ID_VENDA = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, venda.getIdCliente());
            stmt.setTimestamp(2, venda.getDataHoraPagamento() != null ? Timestamp.valueOf(venda.getDataHoraPagamento()) : null);
            stmt.setDouble(3, venda.getValorTotalVenda());
            stmt.setBoolean(4, venda.isStatusPago());
            stmt.setInt(5, venda.getIdVenda());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar venda.", e);
        }
    }

    // metodo para excluir uma venda
    public void deletar(int idVenda) {
        String sql = "DELETE FROM TB_VENDA WHERE ID_VENDA = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir venda.", e);
        }
    }

    // metodo para buscar uma venda pelo ID
    public Venda buscarPorId(int idVenda) {
        String sql = "SELECT * FROM TB_VENDA WHERE ID_VENDA = ?";
        Venda venda = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idVenda);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    venda = new Venda();
                    venda.setIdVenda(rs.getInt("ID_VENDA"));
                    venda.setIdCliente(rs.getInt("ID_CLIENTE"));
                    venda.setDataHoraPagamento(rs.getTimestamp("DH_PAGAMENTO") != null ? rs.getTimestamp("DH_PAGAMENTO").toLocalDateTime() : null);
                    venda.setValorTotalVenda(rs.getDouble("VL_TOTAL_VENDA"));
                    venda.setStatusPago(rs.getBoolean("IC_PAGO"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar venda.", e);
        }

        return venda;
    }

    // metodo para listar todas as vendas
    public List<Venda> listarTodos() {
        String sql = "SELECT * FROM TB_VENDA";
        List<Venda> vendas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Venda venda = new Venda();
                venda.setIdVenda(rs.getInt("ID_VENDA"));
                venda.setIdCliente(rs.getInt("ID_CLIENTE"));
                venda.setDataHoraPagamento(rs.getTimestamp("DH_PAGAMENTO") != null ? rs.getTimestamp("DH_PAGAMENTO").toLocalDateTime() : null);
                venda.setValorTotalVenda(rs.getDouble("VL_TOTAL_VENDA"));
                venda.setStatusPago(rs.getBoolean("IC_PAGO"));
                vendas.add(venda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar vendas.", e);
        }

        return vendas;
    }

}