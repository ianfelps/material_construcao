package views;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaProduto extends JFrame {

    // atributos
    private final JLabel iconeLabel;
    private final JLabel tituloLabel;
    private final JLabel subtituloLabel;
    private final JButton cadastrarButton;
    private final JButton atualizarButton;
    private final JButton deletarButton;
    private final JButton voltarButton;
    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;

    private ProdutoDAO produtoDAO;

    // construtor
    public TelaProduto() {
        super("Gerenciamento de Produtos");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        produtoDAO = new ProdutoDAO();

        // layout
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // fontes
        Font primariaFont = new Font("SansSerif", Font.BOLD, 30);
        Font secundariaFont = new Font("SansSerif", Font.BOLD, 15);

        // icone
        ImageIcon icone = new ImageIcon(getClass().getResource("icone.png"));
        Image img = icone.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        iconeLabel = new JLabel(new ImageIcon(img));
        iconeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // labels
        tituloLabel = new JLabel("Gerenciamento de Produtos");
        tituloLabel.setFont(primariaFont);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtituloLabel = new JLabel("Bem-vindo ao gerenciamento de produtos!");
        subtituloLabel.setFont(secundariaFont);
        subtituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // botoes
        cadastrarButton = new JButton("Adicionar Produto");
        cadastrarButton.setFont(secundariaFont);
        cadastrarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cadastrarButton.addActionListener(new CadastrarActionListener());

        atualizarButton = new JButton("Atualizar Produto");
        atualizarButton.setFont(secundariaFont);
        atualizarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        atualizarButton.addActionListener(new AtualizarActionListener());

        deletarButton = new JButton("Deletar Produto");
        deletarButton.setFont(secundariaFont);
        deletarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deletarButton.addActionListener(new DeletarActionListener());

        voltarButton = new JButton("Voltar");
        voltarButton.setFont(secundariaFont);
        voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        voltarButton.addActionListener(new VoltarActionListener());

        // painel de botoes
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(cadastrarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(atualizarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(deletarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(voltarButton);
        buttonPanel.add(Box.createHorizontalGlue());

        // tabela de produtos
        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "Quantidade", "Valor"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaProdutos = new JTable(modeloTabela);
        JScrollPane pane = new JScrollPane(tabelaProdutos);

        // adicionar elementos
        add(Box.createVerticalStrut(15));
        add(iconeLabel);
        add(Box.createVerticalStrut(15));
        add(tituloLabel);
        add(Box.createVerticalStrut(15));
        add(subtituloLabel);
        add(Box.createVerticalStrut(15));
        add(pane);
        add(Box.createVerticalStrut(15));
        add(buttonPanel);
        add(Box.createVerticalStrut(15));

        // carregar produtos ao abrir a tela
        carregarProdutos();
    }

    // metodo para carregar produtos
    private void carregarProdutos() {
        List<Produto> produtos = produtoDAO.listarTodos();
        modeloTabela.setRowCount(0); // limpar a tabela
        for (Produto produto : produtos) {
            modeloTabela.addRow(new Object[]{
                    produto.getIdProduto(),
                    produto.getNomeProduto(),
                    produto.getQuantidadeProduto(),
                    produto.getValorUnitario()
            });
        }
    }

    // metodo para voltar para a tela principal
    private class VoltarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new TelaMenu().setVisible(true);
        }
    }

    // metodo para o botao de cadastrar produto
    private class CadastrarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Produto produto = new Produto();

            ProdutoDialog dialog = new ProdutoDialog(TelaProduto.this, "Adicionar Produto", produto);
            dialog.setVisible(true);

            if (dialog.isSalvarClicked()) {
                produtoDAO.inserir(produto);
                carregarProdutos();
            }
        }
    }

    // metodo para o botao de atualizar produto
    private class AtualizarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tabelaProdutos.getSelectedRow();
            if (selectedRow != -1) {
                int idProduto = (int) modeloTabela.getValueAt(selectedRow, 0);
                Produto produto = produtoDAO.buscarPorId(idProduto);

                ProdutoDialog dialog = new ProdutoDialog(TelaProduto.this, "Atualizar Produto", produto);
                dialog.setVisible(true);

                if (dialog.isSalvarClicked()) {
                    produtoDAO.atualizar(produto);
                    carregarProdutos();
                }
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Selecione um produto para atualizar.",
                        "Atualizar Produto",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    // metodo para o botao de deletar produto
    private class DeletarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tabelaProdutos.getSelectedRow();
            if (selectedRow != -1) {
                int idProduto = (int) modeloTabela.getValueAt(selectedRow, 0);
                Object[] options = {"Sim", "Não"};
                int confirm = JOptionPane.showOptionDialog(
                        null,
                        "Tem certeza que deseja deletar o produto?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    produtoDAO.deletar(idProduto);
                    modeloTabela.removeRow(selectedRow); // remover a linha da tabela
                    JOptionPane.showMessageDialog(
                            null,
                            "Produto deletado com sucesso!",
                            "Confirmação",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
                carregarProdutos();
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Selecione um produto para deletar.",
                        "Deletar Produto",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }
}