package views;

import dao.VendaDAO;
import dao.ProdutoDAO;
import dao.ProdutoVendaDAO;
import dao.ClienteDAO;
import dao.EnderecoClienteDAO;
import model.Venda;
import model.Produto;
import model.ProdutoVenda;
import model.Cliente;
import model.EnderecoCliente;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TelaRelatorio extends JFrame {
    private final JLabel iconeLabel;
    private final JLabel tituloLabel;
    private final JLabel subtituloLabel;
    private final JButton visualizarButton;
    private final JButton deletarButton;
    private final JButton voltarButton;
    private JTable tabelaVendas;
    private DefaultTableModel modeloTabela;
    private VendaDAO vendaDAO;
    private ClienteDAO clienteDAO;

    public TelaRelatorio() {
        super("Relatórios de Vendas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        vendaDAO = new VendaDAO();
        clienteDAO = new ClienteDAO();

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        Font primariaFont = new Font("SansSerif", Font.BOLD, 30);
        Font secundariaFont = new Font("SansSerif", Font.BOLD, 15);

        ImageIcon icone = new ImageIcon(getClass().getResource("icone.png"));
        Image img = icone.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        iconeLabel = new JLabel(new ImageIcon(img));
        iconeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        tituloLabel = new JLabel("Relatórios de Vendas");
        tituloLabel.setFont(primariaFont);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtituloLabel = new JLabel("Verifique e gerencie as vendas!");
        subtituloLabel.setFont(secundariaFont);
        subtituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // botoes
        visualizarButton = new JButton("Visualizar Venda");
        visualizarButton.setFont(secundariaFont);
        visualizarButton.addActionListener(new VisualizarActionListener());

        deletarButton = new JButton("Deletar Venda");
        deletarButton.setFont(secundariaFont);
        deletarButton.addActionListener(new DeletarActionListener());

        voltarButton = new JButton("Voltar");
        voltarButton.setFont(secundariaFont);
        voltarButton.addActionListener(new VoltarActionListener());

        // botoes lado a lado
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(visualizarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(deletarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(voltarButton);
        buttonPanel.add(Box.createHorizontalGlue());

        modeloTabela = new DefaultTableModel(
                new String[]{"ID", "Cliente", "Data", "Valor Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaVendas = new JTable(modeloTabela);
        JScrollPane pane = new JScrollPane(tabelaVendas);

        add(Box.createVerticalStrut(15));
        add(iconeLabel);
        add(Box.createVerticalStrut(15));
        add(tituloLabel);
        add(Box.createVerticalStrut(15));
        add(subtituloLabel);
        add(Box.createVerticalStrut(15));
        add(pane);
        add(Box.createVerticalStrut(15));
        add(buttonPanel);  // Adicionando o painel com os botões.
        add(Box.createVerticalStrut(15));

        carregarVendas();
    }

    // metodo para voltar para a tela principal
    private class VoltarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            TelaMenu telaMenu = new TelaMenu();
            telaMenu.setLocationRelativeTo(null);
            telaMenu.setVisible(true);
        }
    }

    // metodo para carregar as vendas
    private void carregarVendas() {
        List<Venda> vendas = vendaDAO.listarTodos();
        modeloTabela.setRowCount(0); // limpa a tabela antes de preencher novamente

        for (Venda venda : vendas) {
            Cliente cliente = clienteDAO.buscarPorId(venda.getIdCliente());
            String nomeCliente = cliente != null ? cliente.getNomeCliente() : "Cliente não encontrado";

            modeloTabela.addRow(new Object[]{
                    venda.getIdVenda(),
                    nomeCliente,
                    venda.getDataPagamento() != null ? venda.getDataPagamento().toLocalDate().toString() : "Não Pago",
                    String.format("R$%.2f", venda.getValorTotalVenda())
            });
        }
    }

    // metodo para vizualizar venda
    private class VisualizarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tabelaVendas.getSelectedRow();
            if (selectedRow != -1) {
                int idVenda = (int) modeloTabela.getValueAt(selectedRow, 0);
                Venda venda = vendaDAO.buscarPorId(idVenda);
                Cliente cliente = clienteDAO.buscarPorId(venda.getIdCliente());
                EnderecoClienteDAO enderecoDAO = new EnderecoClienteDAO();
                ProdutoVendaDAO produtoVendaDAO = new ProdutoVendaDAO();
                ProdutoDAO produtoDAO = new ProdutoDAO();

                if (venda != null && cliente != null) {
                    EnderecoCliente endereco = enderecoDAO.listarTodosPorCliente(cliente.getIdCliente()).get(0);
                    List<ProdutoVenda> produtosVenda = produtoVendaDAO.listarPorVendaId(idVenda);
                    DefaultTableModel produtoModeloTabela = new DefaultTableModel(new String[]{"Código", "Nome", "Quantidade", "Preço Unitário", "Preço Total"}, 0);

                    for (ProdutoVenda produtoVenda : produtosVenda) {
                        Produto produto = produtoDAO.buscarPorId(produtoVenda.getCodigoProduto());
                        double precoUnitario = produto.getValorUnitario();
                        int quantidade = produtoVenda.getQuantidadeProdutoRetirado();

                        produtoModeloTabela.addRow(new Object[]{
                                produtoVenda.getCodigoProduto(),
                                produto.getNomeProduto(),
                                quantidade,
                                String.format("R$%.2f", precoUnitario),
                                String.format("R$%.2f", quantidade * precoUnitario)
                        });
                    }

                    // formatar data
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dataFormatada = venda.getDataPagamento() != null ? venda.getDataPagamento().toLocalDate().format(dateFormatter) : "N/A";

                    // painel de detalhes da venda
                    JPanel painelDetalhes = new JPanel();
                    painelDetalhes.setLayout(new BoxLayout(painelDetalhes, BoxLayout.X_AXIS));
                    painelDetalhes.setBorder(new EmptyBorder(10, 0, 10, 0));

                    JPanel coluna1 = new JPanel();
                    coluna1.setLayout(new BoxLayout(coluna1, BoxLayout.Y_AXIS));
                    coluna1.setAlignmentY(Component.TOP_ALIGNMENT);

                    JPanel coluna2 = new JPanel();
                    coluna2.setLayout(new BoxLayout(coluna2, BoxLayout.Y_AXIS));
                    coluna2.setAlignmentY(Component.TOP_ALIGNMENT);

                    // configurar fonte
                    Font primariaFont = new Font("SansSerif", Font.BOLD, 20);
                    Font secundariaFont = new Font("SansSerif", Font.BOLD, 15);

                    // add detalhes na coluna 1
                    JLabel idVendaLabel = new JLabel("ID Venda: " + venda.getIdVenda());
                    idVendaLabel.setFont(secundariaFont);
                    coluna1.add(idVendaLabel);

                    JLabel clienteLabel = new JLabel("Cliente: " + cliente.getNomeCliente());
                    clienteLabel.setFont(secundariaFont);
                    coluna1.add(clienteLabel);

                    JLabel rgLabel = new JLabel("RG: " + cliente.getRg());
                    rgLabel.setFont(secundariaFont);
                    coluna1.add(rgLabel);

                    JLabel cpfLabel = new JLabel("CPF: " + cliente.getCpf());
                    cpfLabel.setFont(secundariaFont);
                    coluna1.add(cpfLabel);

                    // add detalhes na coluna 2
                    JLabel dataLabel = new JLabel("Data: " + dataFormatada);
                    dataLabel.setFont(secundariaFont);
                    coluna2.add(dataLabel);

                    JLabel enderecoLabel = new JLabel("Endereço: " + endereco.getNomeEndereco());
                    enderecoLabel.setFont(secundariaFont);
                    coluna2.add(enderecoLabel);

                    JLabel cidadeLabel = new JLabel("Cidade: " + endereco.getNomeCidade());
                    cidadeLabel.setFont(secundariaFont);
                    coluna2.add(cidadeLabel);

                    JLabel ufLabel = new JLabel("UF: " + endereco.getSiglaUF());
                    ufLabel.setFont(secundariaFont);
                    coluna2.add(ufLabel);

                    JLabel valorTotalLabel = new JLabel(String.format("Valor Total: R$%.2f", venda.getValorTotalVenda()));
                    valorTotalLabel.setFont(primariaFont);
                    coluna2.add(valorTotalLabel);

                    painelDetalhes.add(coluna1);
                    painelDetalhes.add(Box.createHorizontalStrut(50)); // Espaçamento entre colunas
                    painelDetalhes.add(coluna2);

                    // secao de tabela de produtos
                    JTable tabelaProdutos = new JTable(produtoModeloTabela);
                    JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
                    scrollPane.setBorder(new TitledBorder("Produtos Vendidos"));
                    scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);

                    // painel principal
                    JPanel mainPanel = new JPanel();
                    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
                    mainPanel.add(painelDetalhes);
                    mainPanel.add(Box.createVerticalStrut(10)); // Espaçamento entre detalhes e tabela
                    mainPanel.add(scrollPane);

                    // caixa de dialogo
                    JOptionPane.showMessageDialog(null, mainPanel, "Detalhes da Venda", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Informações da venda ou cliente não encontradas.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma venda para visualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // metodo para deletar uma venda
    private class DeletarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tabelaVendas.getSelectedRow();
            if (selectedRow != -1) {
                int idVenda = (int) modeloTabela.getValueAt(selectedRow, 0);

                Object[] options = {"Sim", "Não"};

                // pergunta de confirmacao
                int confirmacao = JOptionPane.showOptionDialog(
                        TelaRelatorio.this,
                        "Você tem certeza que deseja deletar a venda?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (confirmacao == JOptionPane.YES_OPTION) {
                    try {
                        vendaDAO.deletar(idVenda);
                        JOptionPane.showMessageDialog(TelaRelatorio.this, "Venda deletada com sucesso!");
                        carregarVendas();  // atualize a lista de vendas exclusao
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(TelaRelatorio.this, "Erro ao deletar a venda: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(TelaRelatorio.this, "Selecione uma venda para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}