package views;

import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdutoDialog extends JDialog {

    // atributos
    private JTextField nomeField;
    private JTextField quantidadeField;
    private JTextField valorField;
    private boolean salvarClicked = false;
    private Produto produto;

    // construtor
    public ProdutoDialog(Frame parent, String title, Produto produto) {
        super(parent, title, true);
        this.produto = produto;
        iniciarComponentes();
        preencherForm();
        setSize(300, 200);
        setLocationRelativeTo(parent);
    }

    // metodo para iniciar os componentes
    private void iniciarComponentes() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nomeField = new JTextField(20);
        quantidadeField = new JTextField(20);
        valorField = new JTextField(20);

        formPanel.add(criarFieldPanel("Nome:", nomeField));
        formPanel.add(criarFieldPanel("Quantidade:", quantidadeField));
        formPanel.add(criarFieldPanel("Valor:", valorField));

        add(formPanel);

        // painel de botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seSalvar();
            }
        });

        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seCancelar();
            }
        });

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(salvarButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(cancelarButton);
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

    // metodo para preencher o formulario com os dados do produto
    private void preencherForm() {
        if (produto != null) {
            nomeField.setText(produto.getNomeProduto());
            quantidadeField.setText(String.valueOf(produto.getQuantidadeProduto()));
            valorField.setText(String.valueOf(produto.getValorUnitario()));
        }
    }

    // metodo para atualizar os dados do produto
    private void seSalvar() {
        produto.setNomeProduto(nomeField.getText());
        produto.setQuantidadeProduto(Integer.parseInt(quantidadeField.getText()));
        produto.setValorUnitario(Double.parseDouble(valorField.getText()));
        produto.setIdLoja(1);
        salvarClicked = true;
        setVisible(false);
    }

    // metodo para fechar o dialog sem alteracoes
    private void seCancelar() {
        setVisible(false);
    }

    // metodo para verificar se o botao salvar foi acionado
    public boolean isSalvarClicked() {
        return salvarClicked;
    }
}