package dao;

import database.ConnectionFactory;
import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // metodo para verificar se uma loja existe
    public boolean existeLoja(int idLoja) {
        String sql = "SELECT 1 FROM TB_LOJA WHERE ID_LOJA = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idLoja);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar se a loja existe: ", e);
        }
    }

    // metodo para verificar se o produto esta em uma venda
    public boolean produtoEmVenda(int idProduto) {
        String sql = "SELECT 1 FROM TB_PRODUTO_VENDA WHERE CD_PRODUTO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar se o produto está em uma venda: ", e);
        }
    }

    // metodo para inserir um produto
    public boolean inserir(Produto produto) {
        if (!existeLoja(produto.getIdLoja())) {
            System.out.println("Loja com o ID " + produto.getIdLoja() + " não existe.");
            return false;
        }

        String sql = "INSERT INTO TB_PRODUTO (ID_LOJA, NO_PRODUTO, QT_PRODUTO_ESTOQUE, VL_PRODUTO_UNITARIO) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produto.getIdLoja());
            stmt.setString(2, produto.getNomeProduto());
            stmt.setInt(3, produto.getQuantidadeProduto());
            stmt.setDouble(4, produto.getValorUnitario());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir produto: ", e);
        }
    }

    // metodo para atualizar um produto
    public void atualizar(Produto produto) {
        String sql = "UPDATE TB_PRODUTO SET ID_LOJA = ?, NO_PRODUTO = ?, QT_PRODUTO_ESTOQUE = ?, VL_PRODUTO_UNITARIO = ? WHERE CD_PRODUTO = ?";
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

    // metodo para deletar um produto
    public void deletar(int idProduto) {
        // verifica se o produto esta em uma venda
        if (produtoEmVenda(idProduto)) {
            throw new RuntimeException("O produto não pode ser deletado pois está associado a uma venda.");
        }
        String sql = "DELETE FROM TB_PRODUTO WHERE CD_PRODUTO = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProduto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto: ", e);
        }
    }

    // metodo para buscar produto por ID
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
                    produto.setQuantidadeProduto(rs.getInt("QT_PRODUTO_ESTOQUE"));
                    produto.setValorUnitario(rs.getDouble("VL_PRODUTO_UNITARIO"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto: ", e);
        }

        return produto;
    }

    // metodo para buscar produto por nome
    public Produto buscarPorNome(String nome) {
        String sql = "SELECT * FROM TB_PRODUTO WHERE NO_PRODUTO = ?";
        Produto produto = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setIdProduto(rs.getInt("CD_PRODUTO"));
                    produto.setNomeProduto(rs.getString("NO_PRODUTO"));
                    produto.setValorUnitario(rs.getDouble("VL_PRODUTO_UNITARIO"));
                    // Configure outros atributos conforme necessário
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar produto.", e);
        }

        return produto;
    }

    // metodo para listar todos os produtos
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
                produto.setQuantidadeProduto(rs.getInt("QT_PRODUTO_ESTOQUE"));
                produto.setValorUnitario(rs.getDouble("VL_PRODUTO_UNITARIO"));

                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar produtos: ", e);
        }

        return produtos;
    }

}