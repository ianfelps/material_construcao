package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaMenu extends JFrame {

    // atributos
    private final JLabel iconeLabel;
    private final JLabel tituloLabel;
    private final JLabel subtituloLabel;
    private final JButton clienteButton;
    private final JButton produtoButton;
    private final JButton vendaButton;
    private final JButton relatorioButton;
    private final JButton sairButton;

    // construtor
    public TelaMenu() {
        super("Menu Principal");

        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setResizable(true);

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
        tituloLabel = new JLabel("Menu Principal");
        tituloLabel.setFont(primariaFont);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtituloLabel = new JLabel("Bem-vindo ao Sistema de Gerenciamento!");
        subtituloLabel.setFont(secundariaFont);
        subtituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // botoes
        clienteButton = new JButton("Gerenciar Clientes");
        clienteButton.setFont(secundariaFont);
        clienteButton.setPreferredSize(new Dimension(200, 50));
        clienteButton.setMaximumSize(new Dimension(200, 50));
        clienteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        clienteButton.addActionListener(new clienteActionListener());

        produtoButton = new JButton("Gerenciar Produtos");
        produtoButton.setFont(secundariaFont);
        produtoButton.setPreferredSize(new Dimension(200, 50));
        produtoButton.setMaximumSize(new Dimension(200, 50));
        produtoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        produtoButton.addActionListener(new produtoActionListener());

        vendaButton = new JButton("Registrar Venda");
        vendaButton.setFont(secundariaFont);
        vendaButton.setPreferredSize(new Dimension(200, 50));
        vendaButton.setMaximumSize(new Dimension(200, 50));
        vendaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        vendaButton.addActionListener(new vendaActionListener());

        relatorioButton = new JButton("Gerenciar Relatórios");
        relatorioButton.setFont(secundariaFont);
        relatorioButton.setPreferredSize(new Dimension(200, 50));
        relatorioButton.setMaximumSize(new Dimension(200, 50));
        relatorioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        relatorioButton.addActionListener(new relatorioActionListener());

        sairButton = new JButton("Sair");
        sairButton.setFont(secundariaFont);
        sairButton.setPreferredSize(new Dimension(200, 50));
        sairButton.setMaximumSize(new Dimension(200, 50));
        sairButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sairButton.addActionListener(new sairActionListener());

        // adicionar elementos
        add(Box.createVerticalStrut(15));
        add(iconeLabel);
        add(Box.createVerticalStrut(15));
        add(tituloLabel);
        add(Box.createVerticalStrut(15));
        add(subtituloLabel);
        add(Box.createVerticalStrut(20));
        add(clienteButton);
        add(Box.createVerticalStrut(20));
        add(produtoButton);
        add(Box.createVerticalStrut(20));
        add(vendaButton);
        add(Box.createVerticalStrut(20));
        add(relatorioButton);
        add(Box.createVerticalStrut(20));
        add(sairButton);
    }

    // eventos dos botoes
    private class clienteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            TelaCliente telaCliente = new TelaCliente();
            telaCliente.setVisible(true);
        }
    }

    private class produtoActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            TelaProduto telaProduto = new TelaProduto();
            telaProduto.setVisible(true);
        }
    }

    private class vendaActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            TelaVenda telaVenda = new TelaVenda();
            telaVenda.setVisible(true);
        }
    }

    private class relatorioActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            TelaRelatorio telaRelatorio = new TelaRelatorio();
            telaRelatorio.setVisible(true);
        }
    }

    private class sairActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}
