package views;

import dao.AdministradorDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

    // atributos
    private final JLabel iconeLabel;
    private final JLabel tituloLabel;
    private final JLabel subtituloLabel;
    private final JLabel usuarioLabel;
    private final JLabel senhaLabel;
    private final JTextField usuarioField;
    private final JPasswordField senhaField;
    private final JButton confirmarButton;
    private final AdministradorDAO administradorDAO;

    // construtor
    public TelaLogin() {
        super("Login");

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 450);
        setResizable(true);

        // layout da tela
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
        tituloLabel = new JLabel("Login de Administrador");
        tituloLabel.setFont(primariaFont);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtituloLabel = new JLabel("Por favor, insira suas credenciais abaixo.");
        subtituloLabel.setFont(secundariaFont);
        subtituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usuarioLabel = new JLabel("Usuário: ");
        usuarioLabel.setFont(secundariaFont);
        usuarioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        senhaLabel = new JLabel("Senha: ");
        senhaLabel.setFont(secundariaFont);
        senhaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // campos de texto e senha
        usuarioField = new JTextField(1);
        usuarioField.setPreferredSize(new Dimension(200, 25));
        usuarioField.setMaximumSize(new Dimension(200, 25));
        usuarioField.setAlignmentX(Component.CENTER_ALIGNMENT);

        senhaField = new JPasswordField(1);
        senhaField.setPreferredSize(new Dimension(200, 25));
        senhaField.setMaximumSize(new Dimension(200, 25));
        senhaField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // botao
        confirmarButton = new JButton("Confirmar");
        confirmarButton.setFont(secundariaFont);
        confirmarButton.setPreferredSize(new Dimension(150, 50));
        confirmarButton.setMaximumSize(new Dimension(150, 50));
        confirmarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        getRootPane().setDefaultButton(confirmarButton);
        confirmarButton.addActionListener(new confirmarActionListener());

        // adicionar elementos
        add(Box.createVerticalStrut(15));
        add(iconeLabel);
        add(Box.createVerticalStrut(15));
        add(tituloLabel);
        add(Box.createVerticalStrut(15));
        add(subtituloLabel);
        add(Box.createVerticalStrut(20));
        add(usuarioLabel);
        add(usuarioField);
        add(Box.createVerticalStrut(15));
        add(senhaLabel);
        add(senhaField);
        add(Box.createVerticalStrut(30));
        add(confirmarButton);

        // inicializar o AdministradorDAO
        administradorDAO = new AdministradorDAO();
    }

    // eventos dos botoes
    private class confirmarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String usuario = usuarioField.getText();
            String senha = new String(senhaField.getPassword());

            if (administradorDAO.validarLogin(usuario, senha)) {
                // login correto
                JOptionPane.showMessageDialog(TelaLogin.this, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                // abrir tela do menu principal
                dispose();
                TelaMenu telaMenu = new TelaMenu();
                telaMenu.setLocationRelativeTo(null);
                telaMenu.setVisible(true);

            } else {
                // login incorreto
                JOptionPane.showMessageDialog(TelaLogin.this, "Credenciais incorretas!", "Erro", JOptionPane.ERROR_MESSAGE);
                usuarioField.setText("");
                senhaField.setText("");
            }
        }
    }
}