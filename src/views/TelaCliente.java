package views;

import dao.ClienteDAO;
import dao.EnderecoClienteDAO;
import dao.TelefoneClienteDAO;
import model.Cliente;
import model.EnderecoCliente;
import model.TelefoneCliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TelaCliente extends JFrame {

    // atributos
    private final JLabel iconeLabel;
    private final JLabel tituloLabel;
    private final JLabel subtituloLabel;
    private final JButton cadastrarButton;
    private final JButton atualizarButton;
    private final JButton deletarButton;
    private final JButton voltarButton;
    private JTable tabelaClientes;
    private DefaultTableModel modeloTabela;

    private ClienteDAO clienteDAO;
    private EnderecoClienteDAO enderecoClienteDAO;
    private TelefoneClienteDAO telefoneClienteDAO;

    // construtor
    public TelaCliente() {
        super("Gerenciamento de Clientes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        clienteDAO = new ClienteDAO();
        enderecoClienteDAO = new EnderecoClienteDAO();
        telefoneClienteDAO = new TelefoneClienteDAO();

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
        tituloLabel = new JLabel("Gerenciamento de Cliente");
        tituloLabel.setFont(primariaFont);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtituloLabel = new JLabel("Bem-vindo ao gerenciamento de clientes!");
        subtituloLabel.setFont(secundariaFont);
        subtituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // botoes
        cadastrarButton = new JButton("Adicionar cliente");
        cadastrarButton.setFont(secundariaFont);
        cadastrarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cadastrarButton.addActionListener(new CadastrarActionListener());

        atualizarButton = new JButton("Atualizar cliente");
        atualizarButton.setFont(secundariaFont);
        atualizarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        atualizarButton.addActionListener(new AtualizarActionListener());

        deletarButton = new JButton("Deletar cliente");
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

        // tabela de clientes
        modeloTabela = new DefaultTableModel(new String[]{"ID", "Nome", "RG", "CPF", "Tipo", "Telefones", "Endereços"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.addMouseListener(new celulaListener());
        JScrollPane pane = new JScrollPane(tabelaClientes);

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

        // carregar clientes ao abrir a tela
        carregarClientes();
    }

    // metodo para carregar clientes
    private void carregarClientes() {
        List<Cliente> clientes = clienteDAO.listarTodos();
        modeloTabela.setRowCount(0); // limpar a tabela
        for (Cliente cliente : clientes) {
            List<TelefoneCliente> telefones = telefoneClienteDAO.listarTodosPorCliente(cliente.getIdCliente());
            List<EnderecoCliente> enderecos = enderecoClienteDAO.listarTodosPorCliente(cliente.getIdCliente());

            String telefonesStr = telefones.stream()
                    .map(TelefoneCliente::getNumeroTelefone)
                    .collect(Collectors.joining(" | "));

            String enderecosStr = enderecos.stream()
                    .map(endereco -> endereco.getNomeEndereco() + ", " + endereco.getNomeCidade() + ", " + endereco.getSiglaUF())
                    .collect(Collectors.joining(" | "));

            modeloTabela.addRow(new Object[]{
                    cliente.getIdCliente(),
                    cliente.getNomeCliente(),
                    cliente.getRg(),
                    cliente.getCpf(),
                    cliente.getTipoCliente(),
                    telefonesStr,
                    enderecosStr
            });
        }
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

    // metodo para o botao de cadastrar cliente
    private class CadastrarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Cliente cliente = new Cliente();
            List<TelefoneCliente> telefones = new ArrayList<>();
            List<EnderecoCliente> enderecos = new ArrayList<>();

            ClienteDialog dialog = new ClienteDialog(TelaCliente.this, "Adicionar Cliente", cliente, telefones, enderecos);
            dialog.setVisible(true);

            if (dialog.isSalvarClicked()) {
                int idCliente = clienteDAO.inserir(cliente);
                cliente.setIdCliente(idCliente);

                for (TelefoneCliente telefone : telefones) {
                    telefone.setIdCliente(idCliente);
                    telefoneClienteDAO.inserir(telefone);
                }

                for (EnderecoCliente endereco : enderecos) {
                    endereco.setIdCliente(idCliente);
                    enderecoClienteDAO.inserir(endereco);
                }

                carregarClientes();
            }
        }
    }

    // metodo para o botao de atualizar cliente
    private class AtualizarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tabelaClientes.getSelectedRow();
            if (selectedRow != -1) {
                int idCliente = (int) modeloTabela.getValueAt(selectedRow, 0);
                Cliente cliente = clienteDAO.buscarPorId(idCliente);
                List<TelefoneCliente> telefones = telefoneClienteDAO.listarTodosPorCliente(idCliente);
                List<EnderecoCliente> enderecos = enderecoClienteDAO.listarTodosPorCliente(idCliente);

                ClienteDialog dialog = new ClienteDialog(TelaCliente.this, "Atualizar Cliente", cliente, telefones, enderecos);
                dialog.setVisible(true);

                if (dialog.isSalvarClicked()) {
                    clienteDAO.atualizar(cliente);

                    telefoneClienteDAO.deletarPorIdCliente(idCliente);
                    for (TelefoneCliente telefone : telefones) {
                        telefone.setIdCliente(idCliente);
                        telefoneClienteDAO.inserir(telefone);
                    }

                    enderecoClienteDAO.deletarPorIdCliente(idCliente);
                    for (EnderecoCliente endereco : enderecos) {
                        endereco.setIdCliente(idCliente);
                        enderecoClienteDAO.inserir(endereco);
                    }

                    carregarClientes();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um cliente para atualizar.", "Atualizar Cliente", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // metodo para o botao de deletar cliente
    private class DeletarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedRow = tabelaClientes.getSelectedRow();
            if (selectedRow != -1) {
                int idCliente = (int) modeloTabela.getValueAt(selectedRow, 0);
                Object[] options = {"Sim", "Não"};
                int confirm = JOptionPane.showOptionDialog(
                        null,
                        "Tem certeza que deseja deletar o cliente?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    enderecoClienteDAO.deletarPorIdCliente(idCliente);
                    telefoneClienteDAO.deletarPorIdCliente(idCliente);
                    clienteDAO.deletar(idCliente);
                    modeloTabela.removeRow(selectedRow); // remover a linha da tabela
                    // Supondo que o cliente foi deletado com sucesso
                    JOptionPane.showMessageDialog(null, "Cliente deletado com sucesso!", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um cliente para deletar.", "Deletar Cliente", JOptionPane.INFORMATION_MESSAGE);
            }
            // apos deletar um cliente, recarregar a lista de clientes
            carregarClientes();
        }
    }

    // metodo para listar os valores de uma celula da tabela
    private class celulaListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int row = tabelaClientes.rowAtPoint(e.getPoint());
            int col = tabelaClientes.columnAtPoint(e.getPoint());

            if (row >= 0 && (col == 5 || col == 6)) {
                int idCliente = (int) modeloTabela.getValueAt(row, 0);

                if (col == 5) { // Telefone
                    List<TelefoneCliente> telefones = telefoneClienteDAO.listarTodosPorCliente(idCliente);
                    String mensagemTelefones = telefones.stream()
                            .map(TelefoneCliente::getNumeroTelefone)
                            .collect(Collectors.joining("\n"));
                    JOptionPane.showMessageDialog(null, mensagemTelefones, "Telefones do Cliente", JOptionPane.INFORMATION_MESSAGE);
                } else if (col == 6) { // Endereço
                    List<EnderecoCliente> enderecos = enderecoClienteDAO.listarTodosPorCliente(idCliente);
                    String mensagemEnderecos = enderecos.stream()
                            .map(endereco -> endereco.getNomeEndereco() + ", " + endereco.getNomeCidade() + ", " + endereco.getSiglaUF())
                            .collect(Collectors.joining("\n"));
                    JOptionPane.showMessageDialog(null, mensagemEnderecos, "Endereços do Cliente", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}