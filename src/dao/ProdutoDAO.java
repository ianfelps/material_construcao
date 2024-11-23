package dao;

import database.ConnectionFactory;
import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // Inserir Produto
    public void inserir(Produto produto) {
        String sql = "INSERT INTO TB_PRODUTO (ID_LOJA, NO_PRODUTO, QT_PRODUTO, VL_PRODUTO_UNITARIO) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produto.getIdLoja());
            stmt.setString(2, produto.getNomeProduto());
            stmt.setInt(3, produto.getQuantidadeProduto());
            stmt.setDouble(4, produto.getValorUnitario());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir produto: ", e);
        }
    }

    // Listar todos os produtos
    public List<Produto> listarTodos() {
        String sql = "SELECT * FROM TB_PRODUTO";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("CD_PRODUTO"));
                produto.setIdLoja(rs.getInt("ID_LOJA"));
                produto.setNomeProduto(rs.getString("NO_PRODUTO"));
                produto.setQuantidadeProduto(rs.getInt("QT_PRODUTO"));
                produto.setValorUnitario(rs.getDouble("VL_PRODUTO_UNITARIO"));

                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: ", e);
        }

        return produtos;
    }

    // Buscar produto por ID
    public Produto buscarPorId(int idProduto) {
        String sql = "SELECT * FROM TB_PRODUTO WHERE CD_PRODUTO = ?";
        Produto produto = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setIdProduto(rs.getInt("CD_PRODUTO"));
                    produto.setIdLoja(rs.getInt("ID_LOJA"));
                    produto.setNomeProduto(rs.getString("NO_PRODUTO"));
                    produto.setQuantidadeProduto(rs.getInt("QT_PRODUTO"));
                    produto.setValorUnitario(rs.getDouble("VL_PRODUTO_UNITARIO"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto: ", e);
        }

        return produto;
    }

    // Atualizar produto
    public void atualizar(Produto produto) {
        String sql = "UPDATE TB_PRODUTO SET ID_LOJA = ?, NO_PRODUTO = ?, QT_PRODUTO = ?, VL_PRODUTO_UNITARIO = ? WHERE CD_PRODUTO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produto.getIdLoja());
            stmt.setString(2, produto.getNomeProduto());
            stmt.setInt(3, produto.getQuantidadeProduto());
            stmt.setDouble(4, produto.getValorUnitario());
            stmt.setInt(5, produto.getIdProduto());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto: ", e);
        }
    }

    // Deletar produto
    public void deletar(int idProduto) {
        String sql = "DELETE FROM TB_PRODUTO WHERE CD_PRODUTO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto: ", e);
        }
    }
}