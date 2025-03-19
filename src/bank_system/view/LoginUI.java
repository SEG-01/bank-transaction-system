package bank_system.view;

import bank_system.controller.AuthController;
import bank_system.controller.LocalizationManager;
import bank_system.model.User;

import javax.swing.*;
import java.awt.*;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoginUI extends BaseUI {
    private JFrame frame;
    private JLabel titleLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private JComboBox<String> languageCombo; 
    private AuthController authController;

    public LoginUI() {
        this.authController = AuthController.getInstance();
    }
    
    @Override
    public void updateBalanceLabel() {}

    @Override
    public void initializeUI() {

        // 1) Load the current ResourceBundle
        ResourceBundle bundle = LocalizationManager.getBundle();
        
        frame = new JFrame("Login - Bank System");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Panel for input fields
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Add padding

        // Title Label
        titleLabel = new JLabel(bundle.getString("login.title"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing

        // Username Field
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 35));
        usernameField.setMaximumSize(new Dimension(250, 35));
        usernameField.setBorder(BorderFactory.createTitledBorder("login.username"));
        panel.add(usernameField);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 35));
        passwordField.setMaximumSize(new Dimension(250, 35));
        passwordField.setBorder(BorderFactory.createTitledBorder("login.password"));
        panel.add(passwordField);

        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing

        // Login Button
        JButton loginButton = new JButton("Login");
        styleButton(loginButton, new Color(70, 130, 180), Color.BLACK);

        loginButton.addActionListener(e -> attemptLogin());

        languageCombo = new JComboBox<>(new String[]{"English", "Cymraeg"});
        languageCombo.setMaximumSize(new Dimension(120, 30));
        languageCombo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Set initial selection based on current locale
        if ("cy".equals(LocalizationManager.getCurrentLocale().getLanguage())) {
            languageCombo.setSelectedIndex(1);
        } else {
            languageCombo.setSelectedIndex(0);
        }

        languageCombo.addActionListener(e -> {
            String selected = (String) languageCombo.getSelectedItem();
            if ("Cymraeg".equals(selected)) {
                LocalizationManager.setLocale(new Locale("cy", "GB"));
            } else {
                LocalizationManager.setLocale(new Locale("en", "US"));
            }
            refreshUI();
        });

        // Back Button
        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(169, 169, 169), Color.BLACK);

        backButton.addActionListener(e -> {
            frame.dispose();
            new WelcomeUI().initializeUI();
        });

        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void styleButton(JButton button, Color backgroundColor, Color foregroundColor) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 50));
        button.setMaximumSize(new Dimension(150, 50));
        button.setBackground(backgroundColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        User user = authController.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            new BankUI(user).initializeUI();  // Open Bank UI
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void refreshUI() {
        ResourceBundle bundle = LocalizationManager.getBundle();
        frame.setTitle(bundle.getString("login.frameTitle"));
        titleLabel.setText(bundle.getString("login.title"));
        usernameField.setBorder(
            BorderFactory.createTitledBorder(bundle.getString("login.username"))
        );
        passwordField.setBorder(
            BorderFactory.createTitledBorder(bundle.getString("login.password"))
        );
        loginButton.setText(bundle.getString("button.login"));
        backButton.setText(bundle.getString("button.back"));

        frame.revalidate();
        frame.repaint();
    }
}
