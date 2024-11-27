package views;

import model.Cliente;
import model.EnderecoCliente;
import model.TelefoneCliente;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDialog extends JDialog {

    // atributos
    private JTextField nomeField;
    private JTextField rgField;
    private JTextField cpfField;
    private JTextField tipoField;
    private JButton adicionarTelefoneButton;
    private JButton deletarTelefoneButton;
    private JButton adicionarEnderecoButton;
    private JButton deletarEnderecoButton;
    private JList<String> telefoneList;
    private JList<String> enderecoList;
    private DefaultListModel<String> telefoneListModel;
    private DefaultListModel<String> enderecoListModel;

    private Cliente cliente;
    private List<TelefoneCliente> telefones;
    private List<EnderecoCliente> enderecos;
    private boolean saveClicked;

    // construtor
    public ClienteDialog(Frame parent, String title, Cliente cliente, List<TelefoneCliente> telefones, List<EnderecoCliente> enderecos) {
        super(parent, title, true);
        this.cliente = cliente;
        this.telefones = telefones != null ? telefones : new ArrayList<>();
        this.enderecos = enderecos != null ? enderecos : new ArrayList<>();
        iniciarComponentes();
        preencherForm();
        setSize(500, 500);
        setLocationRelativeTo(parent);
    }

    // metodo para inicializar os componentes da interface
    private void iniciarComponentes() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nomeField = new JTextField(20);
        rgField = new JTextField(20);
        cpfField = new JTextField(20);
        tipoField = new JTextField(20);

        formPanel.add(criarFieldPanel("Nome:", nomeField));
        formPanel.add(criarFieldPanel("RG:", rgField));
        formPanel.add(criarFieldPanel("CPF:", cpfField));
        formPanel.add(criarFieldPanel("Tipo:", tipoField));

        telefoneListModel = new DefaultListModel<>();
        telefoneList = new JList<>(telefoneListModel);
        telefoneList.setVisibleRowCount(4);
        adicionarTelefoneButton = new JButton("Adicionar Telefone");
        adicionarTelefoneButton.addActionListener(e -> inserirTelefone());
        deletarTelefoneButton = new JButton("Deletar Telefone");
        deletarTelefoneButton.addActionListener(e -> deletarTelefone());
        formPanel.add(adicionarFieldPanel(new JScrollPane(telefoneList), "Telefones:", adicionarTelefoneButton, deletarTelefoneButton));

        enderecoListModel = new DefaultListModel<>();
        enderecoList = new JList<>(enderecoListModel);
        enderecoList.setVisibleRowCount(4);
        adicionarEnderecoButton = new JButton("Adicionar Endereço");
        adicionarEnderecoButton.addActionListener(e -> inserirEndereco());
        deletarEnderecoButton = new JButton("Deletar Endereço");
        deletarEnderecoButton.addActionListener(e -> deletarEndereco());
        formPanel.add(adicionarFieldPanel(new JScrollPane(enderecoList), "Endereços:", adicionarEnderecoButton, deletarEnderecoButton));

        add(formPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton saveButton = new JButton("Salvar");
        saveButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, saveButton.getMinimumSize().height));
        saveButton.addActionListener(e -> seSalvar());
        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, cancelButton.getMinimumSize().height));
        cancelButton.addActionListener(e -> seCancelar());

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalGlue());

        add(buttonPanel);
    }

    // metodo para criar paineis de rotulo e campo de texto (utilizados no formulario)
    private JPanel criarFieldPanel(String label, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        panel.add(new JLabel(label));
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(field);
        return panel;
    }

    // metodo para criar o painel e adiciona botoes
    private JPanel adicionarFieldPanel(JScrollPane scrollPane, String label, JButton addButton, JButton deleteButton) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BorderLayout());
        labelPanel.add(new JLabel(label), BorderLayout.WEST);
        panel.add(labelPanel);

        panel.add(scrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createHorizontalGlue());

        panel.add(buttonPanel);

        return panel;
    }

    // metodo para preencher o formulario com os dados do cliente
    private void preencherForm() {
        if (cliente != null) {
            nomeField.setText(cliente.getNomeCliente());
            rgField.setText(String.valueOf(cliente.getRg()));
            cpfField.setText(cliente.getCpf());
            tipoField.setText(cliente.getTipoCliente());

            if (telefones != null) {
                for (TelefoneCliente telefone : telefones) {
                    telefoneListModel.addElement(telefone.getNumeroTelefone());
                }
            }

            if (enderecos != null) {
                for (EnderecoCliente endereco : enderecos) {
                    enderecoListModel.addElement(endereco.getNomeEndereco() + ", " + endereco.getNomeCidade() + ", " + endereco.getSiglaUF());
                }
            }
        }
    }

    // metodo para adiconar um telefone
    private void inserirTelefone() {
        String telefone = JOptionPane.showInputDialog(this, "Digite o telefone:", "Inserir Telefone", JOptionPane.QUESTION_MESSAGE);
        if (telefone != null && !telefone.isEmpty()) {
            telefoneListModel.addElement(telefone);
        }
    }

    // metodo para deletar um telefone
    private void deletarTelefone() {
        int selectedIndex = telefoneList.getSelectedIndex();
        if (selectedIndex != -1) {
            Object[] options = {"Sim", "Não"};
            int result = JOptionPane.showOptionDialog(
                    this,
                    "Você tem certeza que deseja deletar este telefone?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (result == JOptionPane.YES_OPTION) {
                telefoneListModel.remove(selectedIndex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um telefone para deletar.", "Deletar Telefone", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // metodo para adiconar um endereco
    private void inserirEndereco() {
        JPanel enderecoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField enderecoField = new JTextField(20);
        JTextField cidadeField = new JTextField(20);
        JTextField ufField = new JTextField(2);

        enderecoPanel.add(new JLabel("Endereço:"));
        enderecoPanel.add(enderecoField);
        enderecoPanel.add(new JLabel("Cidade:"));
        enderecoPanel.add(cidadeField);
        enderecoPanel.add(new JLabel("UF:"));
        enderecoPanel.add(ufField);

        enderecoPanel.setPreferredSize(new Dimension(300, 100));

        int result = JOptionPane.showConfirmDialog(this, enderecoPanel, "Adicionar Endereço", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String endereco = enderecoField.getText() + ", " + cidadeField.getText() + ", " + ufField.getText();
            if (!enderecoField.getText().isEmpty() && !cidadeField.getText().isEmpty() && !ufField.getText().isEmpty()) {
                enderecoListModel.addElement(endereco);
            } else {
                JOptionPane.showMessageDialog(this, "Todos os campos de endereço são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // metodo para deletar um endereco
    private void deletarEndereco() {
        int selectedIndex = enderecoList.getSelectedIndex();
        if (selectedIndex != -1) {
            Object[] options = {"Sim", "Não"};
            int result = JOptionPane.showOptionDialog(
                    this,
                    "Você tem certeza que deseja deletar este endereço?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (result == JOptionPane.YES_OPTION) {
                enderecoListModel.remove(selectedIndex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um endereço para deletar.", "Deletar Endereço", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // metodo para atualizar os dados do cliente
    private void seSalvar() {
        cliente.setNomeCliente(nomeField.getText());
        cliente.setRg(Integer.parseInt(rgField.getText()));
        cliente.setCpf(cpfField.getText());
        cliente.setTipoCliente(tipoField.getText());

        telefones.clear();
        for (int i = 0; i < telefoneListModel.size(); i++) {
            TelefoneCliente telefone = new TelefoneCliente();
            telefone.setNumeroTelefone(telefoneListModel.getElementAt(i));
            telefones.add(telefone);
        }

        enderecos.clear();
        for (int i = 0; i < enderecoListModel.size(); i++) {
            String[] enderecoParts = enderecoListModel.getElementAt(i).split(", ");
            EnderecoCliente endereco = new EnderecoCliente();
            endereco.setNomeEndereco(enderecoParts[0]);
            endereco.setNomeCidade(enderecoParts[1]);
            endereco.setSiglaUF(enderecoParts[2]);
            enderecos.add(endereco);
        }

        saveClicked = true;
        setVisible(false);
    }

    // metodo para fechar o dialog sem alteracoes
    private void seCancelar() {
        dispose();
    }

    // metodo para verificar se o botao salvar foi acionado
    public boolean isSalvarClicked() {
        return saveClicked;
    }
}