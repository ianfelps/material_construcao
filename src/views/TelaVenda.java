package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaVenda extends JFrame {

    // Atributos
    private final JTextField clienteIdField;
    private final JTextField dataPagamentoField;
    private final JTable produtosTable;
    private final DefaultTableModel tableModel;
    private final JButton adicionarProdutoButton;
    private final JButton removerProdutoButton;
    private final JButton registrarVendaButton;
    private final JButton voltarButton;

    public TelaVenda() {
        super("Registrar Venda");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Layout principal
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Ícone
        ImageIcon icone = new ImageIcon(getClass().getResource("icone.png"));
        Image img = icone.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JLabel iconeLabel = new JLabel(new ImageIcon(img));
        iconeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Título e subtítulo
        JLabel tituloLabel = new JLabel("Gerenciamento de Vendas");
        tituloLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtituloLabel = new JLabel("Preencha os campos abaixo para registrar uma venda!");
        subtituloLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        subtituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Painel de entrada de dados
        JPanel entradaPanel = new JPanel();
        entradaPanel.setLayout(new BoxLayout(entradaPanel, BoxLayout.Y_AXIS));
        entradaPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel clienteIdPanel = new JPanel();
        clienteIdPanel.setLayout(new BoxLayout(clienteIdPanel, BoxLayout.X_AXIS));
        clienteIdPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        clienteIdPanel.add(new JLabel("ID do Cliente:"));
        clienteIdField = new JTextField();
        clienteIdField.setMaximumSize(new Dimension(200, 20));
        clienteIdPanel.add(clienteIdField);

        JPanel dataPagamentoPanel = new JPanel();
        dataPagamentoPanel.setLayout(new BoxLayout(dataPagamentoPanel, BoxLayout.X_AXIS));
        dataPagamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dataPagamentoPanel.add(new JLabel("Data de Pagamento (yyyy-MM-dd HH:mm):"));
        dataPagamentoField = new JTextField();
        dataPagamentoField.setMaximumSize(new Dimension(200, 20));
        dataPagamentoPanel.add(dataPagamentoField);

        entradaPanel.add(clienteIdPanel);
        entradaPanel.add(Box.createVerticalStrut(10)); // Espaçamento entre os campos
        entradaPanel.add(dataPagamentoPanel);
        entradaPanel.add(Box.createVerticalStrut(20)); // Espaçamento abaixo do painel de entrada

        // Modelo da Tabela
        tableModel = new DefaultTableModel(new String[]{"Produto", "Quantidade", "Preço"}, 0);
        produtosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(produtosTable);
        scrollPane.setPreferredSize(new Dimension(550, 200));

        // Botões
        JPanel botoesPanel = new JPanel();
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.X_AXIS));
        botoesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        adicionarProdutoButton = new JButton("Adicionar Produto");
        adicionarProdutoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String produto = JOptionPane.showInputDialog("Nome do Produto:");
                String quantidadeStr = JOptionPane.showInputDialog("Quantidade:");
                String precoStr = JOptionPane.showInputDialog("Preço:");

                try {
                    int quantidade = Integer.parseInt(quantidadeStr);
                    double preco = Double.parseDouble(precoStr);
                    tableModel.addRow(new Object[]{produto, quantidade, preco});
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Quantidade ou preço inválido!");
                }
            }
        });

        removerProdutoButton = new JButton("Remover Produto");
        removerProdutoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = produtosTable.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um produto para remover!");
                }
            }
        });

        registrarVendaButton = new JButton("Registrar Venda");
        registrarVendaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clienteId = clienteIdField.getText();
                String dataPagamento = dataPagamentoField.getText();

                if (clienteId.isEmpty() || dataPagamento.isEmpty() || tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos e adicione ao menos um produto!");
                } else {
                    // Código para registrar venda (exemplo simples de confirmação)
                    JOptionPane.showMessageDialog(null, "Venda registrada com sucesso!");

                    // Limpar campos e tabela após registrar venda
                    clienteIdField.setText("");
                    dataPagamentoField.setText("");
                    tableModel.setRowCount(0);
                }
            }
        });

        voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TelaMenu telaMenu = new TelaMenu();
                telaMenu.setVisible(true);
            }
        });

        botoesPanel.add(adicionarProdutoButton);
        botoesPanel.add(Box.createHorizontalStrut(10)); // Espaçamento entre botões
        botoesPanel.add(removerProdutoButton);
        botoesPanel.add(Box.createHorizontalStrut(10)); // Espaçamento entre botões
        botoesPanel.add(registrarVendaButton);
        botoesPanel.add(Box.createHorizontalStrut(10)); // Espaçamento entre botões
        botoesPanel.add(voltarButton);

        // Adicionar componentes ao frame
        add(Box.createVerticalStrut(15)); // Espaçamento superior
        add(iconeLabel);
        add(Box.createVerticalStrut(15));
        add(tituloLabel);
        add(Box.createVerticalStrut(15));
        add(subtituloLabel);
        add(Box.createVerticalStrut(20));
        add(entradaPanel);
        add(Box.createVerticalStrut(20));
        add(scrollPane);
        add(Box.createVerticalStrut(20));
        add(botoesPanel);
        add(Box.createVerticalStrut(20)); // Espaçamento inferior

        setVisible(true);
    }
}