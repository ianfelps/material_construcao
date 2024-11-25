package dao;

import database.ConnectionFactory;
import model.ProdutoVenda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoVendaDAO {

    // metodo para adicionar um novo produto numa venda
    public void inserir(ProdutoVenda produtoVenda) {
        String sqlSelectProdutoEstoque = "SELECT QT_PRODUTO_ESTOQUE, VL_PRODUTO_UNITARIO FROM TB_PRODUTO WHERE CD_PRODUTO = ?";
        String sqlInsertProdutoVenda = "INSERT INTO TB_PRODUTO_VENDA (ID_VENDA, CD_PRODUTO, QT_PRODUTO_RETIRADO, VL_TOTAL_PRODUTO) VALUES (?, ?, ?, ?)";
        String sqlUpdateProdutoEstoque = "UPDATE TB_PRODUTO SET QT_PRODUTO_ESTOQUE = QT_PRODUTO_ESTOQUE - ? WHERE CD_PRODUTO = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            // inicia uma transacao
            conn.setAutoCommit(false);

            // verifica se ha estoque suficiente
            try (PreparedStatement stmtSelect = conn.prepareStatement(sqlSelectProdutoEstoque)) {
                stmtSelect.setInt(1, produtoVenda.getCodigoProduto());

                try (ResultSet rs = stmtSelect.executeQuery()) {
                    if (rs.next()) {
                        int estoqueAtual = rs.getInt("QT_PRODUTO_ESTOQUE");
                        double precoUnitario = rs.getDouble("VL_PRODUTO_UNITARIO");

                        if (estoqueAtual < produtoVenda.getQuantidadeProdutoRetirado()) {
                            throw new RuntimeException("Estoque insuficiente para o produto com código: " + produtoVenda.getCodigoProduto());
                        }

                        // Definir o valor total do produto
                        produtoVenda.setValorTotalProduto(precoUnitario * produtoVenda.getQuantidadeProdutoRetirado());
                    } else {
                        throw new RuntimeException("Produto não encontrado com código: " + produtoVenda.getCodigoProduto());
                    }
                }
            }

            // insere produto na venda
            try (PreparedStatement stmtProdutoVenda = conn.prepareStatement(sqlInsertProdutoVenda, Statement.RETURN_GENERATED_KEYS)) {
                stmtProdutoVenda.setInt(1, produtoVenda.getIdVenda());
                stmtProdutoVenda.setInt(2, produtoVenda.getCodigoProduto());
                stmtProdutoVenda.setInt(3, produtoVenda.getQuantidadeProdutoRetirado());
                stmtProdutoVenda.setDouble(4, produtoVenda.getValorTotalProduto());

                stmtProdutoVenda.executeUpdate();

                try (ResultSet rs = stmtProdutoVenda.getGeneratedKeys()) {
                    if (rs.next()) {
                        produtoVenda.setIdProdutoVenda(rs.getInt(1));
                    }
                }
            }

            // atualiza estoque do produto
            try (PreparedStatement stmtAtualizaEstoque = conn.prepareStatement(sqlUpdateProdutoEstoque)) {
                stmtAtualizaEstoque.setInt(1, produtoVenda.getQuantidadeProdutoRetirado());
                stmtAtualizaEstoque.setInt(2, produtoVenda.getCodigoProduto());

                stmtAtualizaEstoque.executeUpdate();
            }

            // commit da transacao
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao adicionar produto na venda.", e);
        }
    }

    // metodo para excluir um produto numa venda
    public void deletar(int idProdutoVenda) {
        String sql = "DELETE FROM TB_PRODUTO_VENDA WHERE ID_PRODUTO_VENDA = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProdutoVenda);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir produto da venda.", e);
        }
    }

    // metodo para listar todos os produtos numa venda
    public List<ProdutoVenda> listarTodos() {
        String sql = "SELECT * FROM TB_PRODUTO_VENDA";
        List<ProdutoVenda> produtosVenda = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProdutoVenda produtoVenda = new ProdutoVenda();
                produtoVenda.setIdProdutoVenda(rs.getInt("ID_PRODUTO_VENDA"));
                produtoVenda.setIdVenda(rs.getInt("ID_VENDA"));
                produtoVenda.setCodigoProduto(rs.getInt("CD_PRODUTO"));
                produtoVenda.setQuantidadeProdutoRetirado(rs.getInt("QT_PRODUTO_RETIRADO"));
                produtoVenda.setValorTotalProduto(rs.getDouble("VL_TOTAL_PRODUTO"));
                produtosVenda.add(produtoVenda);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar produtos na venda.", e);
        }

        return produtosVenda;
    }

}