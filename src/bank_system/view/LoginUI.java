package bank_system.view;

import bank_system.controller.AuthController;
import bank_system.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginUI {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private AuthController authController;

    public LoginUI(AuthController authController) {
        this.authController = authController;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Login - Bank System");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center window on screen

        // Panel for input fields
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40)); // Add padding

        // Title Label
        JLabel titleLabel = new JLabel("User Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing

        // Username Field
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 35));
        usernameField.setMaximumSize(new Dimension(250, 35));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        panel.add(usernameField);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing

        // Password Field
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 35));
        passwordField.setMaximumSize(new Dimension(250, 35));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        panel.add(passwordField);

        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setBackground(new Color(150, 150, 250)); // Blue color
        loginButton.setForeground(Color.BLUE);
        loginButton.setFocusPainted(false);
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.addActionListener(e -> attemptLogin());

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.addActionListener(e -> {
            frame.dispose();
            new WelcomeUI(authController);
        });

        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        User user = authController.login(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(frame, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            new BankUI(user);  // Open Bank UI
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
