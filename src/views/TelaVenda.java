package views;

import dao.ClienteDAO;
import dao.ProdutoDAO;
import dao.ProdutoVendaDAO;
import dao.VendaDAO;
import model.Cliente;
import model.Produto;
import model.ProdutoVenda;
import model.Venda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TelaVenda extends JFrame {

    // atributos
    private JComboBox<String> clienteComboBox;
    private JTextField dataPagamentoField;
    private JTable produtosTable;
    private DefaultTableModel tableModel;
    private JButton adicionarProdutoButton;
    private JButton removerProdutoButton;
    private JButton registrarVendaButton;
    private JButton voltarButton;
    private List<String> clientes;
    private List<String> produtos;

    // construtor
    public TelaVenda() {
        super("Registrar Venda");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // inicializacao das listas de clientes e produtos
        carregarClientes();
        carregarProdutos();

        // configurar componentes da GUI
        configurarFormulario();

        setVisible(true);
    }

    // metodo para configurar o formulario
    private void configurarFormulario() {
        Font primariaFont = new Font("SansSerif", Font.BOLD, 30);
        Font secundariaFont = new Font("SansSerif", Font.BOLD, 15);

        // icone
        ImageIcon icone = new ImageIcon(getClass().getResource("icone.png"));
        Image img = icone.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel iconeLabel = new JLabel(new ImageIcon(img));
        iconeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // labels
        JLabel tituloLabel = new JLabel("Registrar Venda");
        tituloLabel.setFont(primariaFont);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtituloLabel = new JLabel("Gerencie suas vendas de forma eficiente!");
        subtituloLabel.setFont(secundariaFont);
        subtituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // painel de cliente
        clienteComboBox = new JComboBox<>(clientes.toArray(new String[0]));
        clienteComboBox.setMaximumSize(new Dimension(200, 25));  // Define um tamanho máximo para o ComboBox
        clienteComboBox.setSelectedIndex(0);
        JPanel clientePanel = new JPanel();
        clientePanel.setLayout(new BoxLayout(clientePanel, BoxLayout.X_AXIS));
        clientePanel.add(new JLabel("Cliente:"));
        clientePanel.add(Box.createRigidArea(new Dimension(5, 0)));
        clientePanel.add(clienteComboBox);
        clientePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // painel de produto
        JComboBox<String> produtoComboBox = new JComboBox<>(produtos.toArray(new String[0]));
        produtoComboBox.setMaximumSize(new Dimension(200, 25));
        produtoComboBox.setSelectedIndex(0); // Define o primeiro elemento (espaço vazio) como padrão
        JPanel produtoPanel = new JPanel();
        produtoPanel.setLayout(new BoxLayout(produtoPanel, BoxLayout.X_AXIS));
        produtoPanel.add(new JLabel("Produto:"));
        produtoPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        produtoPanel.add(produtoComboBox);
        produtoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // painel de data
        dataPagamentoField = new JTextField();
        dataPagamentoField.setMaximumSize(new Dimension(200, 20));

        // texto de placeholder
        String placeholder = "yyyy-mm-dd hh:mm:ss";
        dataPagamentoField.setText(placeholder);
        dataPagamentoField.setForeground(Color.GRAY);

        // simular o comportamento de placeholder
        dataPagamentoField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (dataPagamentoField.getText().equals(placeholder)) {
                    dataPagamentoField.setText("");
                    dataPagamentoField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (dataPagamentoField.getText().isEmpty()) {
                    dataPagamentoField.setText(placeholder);
                    dataPagamentoField.setForeground(Color.GRAY);
                }
            }
        });

        JPanel dataPagamentoPanel = new JPanel();
        dataPagamentoPanel.setLayout(new BoxLayout(dataPagamentoPanel, BoxLayout.X_AXIS));
        dataPagamentoPanel.add(new JLabel("Data de Pagamento:"));
        dataPagamentoPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        dataPagamentoPanel.add(dataPagamentoField);
        dataPagamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // configuração da tabela de produtos
        tableModel = new DefaultTableModel(new String[]{"Produto", "Quantidade", "Valor Unitário", "Valor Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // torna todas as células não editáveis
            }
        };
        produtosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(produtosTable);
        scrollPane.setPreferredSize(new Dimension(750, 150));

        // botoes
        adicionarProdutoButton = new JButton("Adicionar Produto");
        adicionarProdutoButton.setFont(secundariaFont);
        adicionarProdutoButton.addActionListener(e -> adicionarProduto());

        removerProdutoButton = new JButton("Remover Produto");
        removerProdutoButton.setFont(secundariaFont);
        removerProdutoButton.addActionListener(e -> removerProduto());

        registrarVendaButton = new JButton("Registrar Venda");
        registrarVendaButton.setFont(secundariaFont);
        registrarVendaButton.addActionListener(e -> registrarVenda());

        voltarButton = new JButton("Voltar");
        voltarButton.setFont(secundariaFont);
        voltarButton.addActionListener(new VoltarActionListener());

        // painel de botoes
        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.add(Box.createHorizontalGlue());
        botoesPanel.add(adicionarProdutoButton);
        botoesPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        botoesPanel.add(removerProdutoButton);
        botoesPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        botoesPanel.add(registrarVendaButton);
        botoesPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        botoesPanel.add(voltarButton);
        botoesPanel.add(Box.createHorizontalGlue());

        // adicionar componentes
        add(Box.createVerticalStrut(15));
        add(iconeLabel);
        add(Box.createVerticalStrut(15));
        add(tituloLabel);
        add(Box.createVerticalStrut(15));
        add(subtituloLabel);
        add(Box.createVerticalStrut(15));
        add(clientePanel);
        add(Box.createVerticalStrut(15));
        add(dataPagamentoPanel);
        add(Box.createVerticalStrut(15));
        add(scrollPane);
        add(Box.createVerticalStrut(15));
        add(botoesPanel);
        add(Box.createVerticalStrut(15));
    }

    // metodo para voltar para a tela principal
    private class VoltarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new TelaMenu().setVisible(true);
        }
    }

    // metodo para adicionar produtos na tabela
    private void adicionarProduto() {
        String produtoSelecionado = (String) JOptionPane.showInputDialog(null,
                "Selecionar Produto", "Produto", JOptionPane.QUESTION_MESSAGE,
                null, produtos.toArray(), produtos.get(0));

        // verificar se um produto valido foi selecionado
        if (produtoSelecionado != null && !produtoSelecionado.isEmpty()) {
            String quantidadeStr = JOptionPane.showInputDialog("Quantidade:");
            try {
                int quantidade = Integer.parseInt(quantidadeStr);
                ProdutoDAO produtoDAO = new ProdutoDAO();
                Produto produto = produtoDAO.buscarPorNome(produtoSelecionado);

                if (produto != null) {
                    double valorUnitario = produto.getValorUnitario();
                    double valorTotal = valorUnitario * quantidade;
                    tableModel.addRow(new Object[]{
                            produtoSelecionado,
                            quantidade,
                            String.format("R$%.2f", valorUnitario),
                            String.format("R$%.2f", valorTotal)
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Produto não encontrado!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Quantidade inválida!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto válido!");
        }
    }

    // metodo para remover um produto da tabela
    private void removerProduto() {
        int selectedRow = produtosTable.getSelectedRow();
        if (selectedRow >= 0) {
            tableModel.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto para remover!");
        }
    }

    // metodo para registrar venda
    private void registrarVenda() {
        // calcular o valor total
        double valorTotalVenda = calculaTotalVenda();

        Object[] options = {"Sim", "Não"};

        // pop up de confirmacao
        int confirmacao = JOptionPane.showOptionDialog(
                this,
                "O valor total da venda é " + String.format("R$%.2f", valorTotalVenda) + ". Deseja continuar?",
                "Confirmação de Venda",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        // selecionar nao, cancela o registro
        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        String clienteSelecionado = (String) clienteComboBox.getSelectedItem();
        String dataPagamento = dataPagamentoField.getText();

        if (clienteSelecionado == null || clienteSelecionado.isEmpty() || tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione um cliente válido e adicione ao menos um produto!");
            return;
        }

        try {
            // obter o id do cliente
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente cliente = clienteDAO.buscarPorNome(clienteSelecionado);
            if (cliente == null) {
                throw new RuntimeException("Cliente não encontrado.");
            }

            // instancia da venda
            Venda venda = new Venda();
            venda.setIdCliente(cliente.getIdCliente());
            venda.setDataHoraPagamento(dataPagamento.isEmpty() ? null : Timestamp.valueOf(dataPagamento).toLocalDateTime());
            venda.setValorTotalVenda(valorTotalVenda);
            venda.setStatusPago(!dataPagamento.isEmpty());

            // inserir venda no banco
            VendaDAO vendaDAO = new VendaDAO();
            vendaDAO.inserir(venda);

            // registro produtos da venda
            ProdutoVendaDAO produtoVendaDAO = new ProdutoVendaDAO();
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String produtoNome = (String) tableModel.getValueAt(i, 0);
                int quantidade = (int) tableModel.getValueAt(i, 1);
                Produto produto = new ProdutoDAO().buscarPorNome(produtoNome);

                if (produto != null) {
                    ProdutoVenda produtoVenda = new ProdutoVenda();
                    produtoVenda.setIdVenda(venda.getIdVenda());
                    produtoVenda.setCodigoProduto(produto.getIdProduto());
                    produtoVenda.setQuantidadeProdutoRetirado(quantidade);

                    produtoVendaDAO.inserir(produtoVenda);
                } else {
                    throw new RuntimeException("Produto não encontrado: " + produtoNome);
                }
            }

            JOptionPane.showMessageDialog(null, "Venda registrada com sucesso!");
            limparFormulario();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao registrar a venda: " + e.getMessage());
        }
    }

    // metodo para calcular o total da venda
    private double calculaTotalVenda() {
        double total = 0.0;
        ProdutoDAO produtoDAO = new ProdutoDAO();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String produtoNome = (String) tableModel.getValueAt(i, 0);
            int quantidade = (int) tableModel.getValueAt(i, 1);
            Produto produto = produtoDAO.buscarPorNome(produtoNome);

            if (produto != null) {
                total += produto.getValorUnitario() * quantidade;
            }
        }

        return total;
    }

    // metodo para limpar o formulario
    private void limparFormulario() {
        clienteComboBox.setSelectedIndex(0);
        dataPagamentoField.setText("");
        tableModel.setRowCount(0);
    }

    // metodo para carregar a lista de clientes
    private void carregarClientes() {
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> listaClientes = clienteDAO.listarTodos();
        clientes = new ArrayList<>();

        clientes.add("");

        for (Cliente cliente : listaClientes) {
            clientes.add(cliente.getNomeCliente());
        }
    }

    //metodo para carregar a lista de produtos
    private void carregarProdutos() {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> listaProdutos = produtoDAO.listarTodos();
        produtos = new ArrayList<>();

        produtos.add("");

        for (Produto produto : listaProdutos) {
            produtos.add(produto.getNomeProduto());
        }
    }
}