package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaProduto extends JFrame {
    private final JLabel iconeLabel;
    private final JLabel tituloLabel;
    private final JLabel subtituloLabel;
    private final JButton consultarButton;
    private final JButton cadastrarButton;
    private final JButton atualizarButton;
    private final JButton deletarButton;
    private final JButton voltarButton;

    // construtor
    public TelaProduto() {
        super("Gerenciamento de Produtos");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        tituloLabel = new JLabel("Gerenciamento de Produtos");
        tituloLabel.setFont(primariaFont);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtituloLabel = new JLabel("Bem-vindo ao gerenciamento de produtos!");
        subtituloLabel.setFont(secundariaFont);
        subtituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // botoes
        consultarButton = new JButton("Listar Produtos");
        consultarButton.setFont(secundariaFont);
        consultarButton.setPreferredSize(new Dimension(200, 50));
        consultarButton.setMaximumSize(new Dimension(200, 50));
        consultarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        cadastrarButton = new JButton("Adicionar Produto");
        cadastrarButton.setFont(secundariaFont);
        cadastrarButton.setPreferredSize(new Dimension(200, 50));
        cadastrarButton.setMaximumSize(new Dimension(200, 50));
        cadastrarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        atualizarButton = new JButton("Atualizar Produto");
        atualizarButton.setFont(secundariaFont);
        atualizarButton.setPreferredSize(new Dimension(200, 50));
        atualizarButton.setMaximumSize(new Dimension(200, 50));
        atualizarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        deletarButton = new JButton("Deletar Produto");
        deletarButton.setFont(secundariaFont);
        deletarButton.setPreferredSize(new Dimension(200, 50));
        deletarButton.setMaximumSize(new Dimension(200, 50));
        deletarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        voltarButton = new JButton("Voltar");
        voltarButton.setFont(secundariaFont);
        voltarButton.setPreferredSize(new Dimension(200, 50));
        voltarButton.setMaximumSize(new Dimension(200, 50));
        voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        voltarButton.addActionListener(new TelaProduto.voltarActionListener());

        // adicionar elementos
        add(Box.createVerticalStrut(15));
        add(iconeLabel);
        add(Box.createVerticalStrut(15));
        add(tituloLabel);
        add(Box.createVerticalStrut(15));
        add(subtituloLabel);
        add(Box.createVerticalStrut(15));
        add(consultarButton);
        add(Box.createVerticalStrut(20));
        add(cadastrarButton);
        add(Box.createVerticalStrut(20));
        add(atualizarButton);
        add(Box.createVerticalStrut(20));
        add(deletarButton);
        add(Box.createVerticalStrut(20));
        add(voltarButton);
    }

    // eventos dos botoes
    private class voltarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            TelaMenu telaMenu = new TelaMenu();
            telaMenu.setVisible(true);
        }
    }
}
