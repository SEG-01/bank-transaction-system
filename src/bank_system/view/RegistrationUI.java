package bank_system.view;

import bank_system.controller.AuthController;

import javax.swing.*;
import java.awt.*;

public class RegistrationUI {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private AuthController authController;

    public RegistrationUI(AuthController authController) {
        this.authController = authController;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Register - Bank System");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titleLabel = new JLabel("User Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

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

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing

        // Confirm Password Field
        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(250, 35));
        confirmPasswordField.setMaximumSize(new Dimension(250, 35));
        confirmPasswordField.setBorder(BorderFactory.createTitledBorder("Confirm Password"));
        panel.add(confirmPasswordField);

        panel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacing

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerButton.setBackground(new Color(50, 150, 250)); // Blue color
        registerButton.setForeground(Color.BLUE);
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(120, 40));
        registerButton.addActionListener(e -> attemptRegistration());

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(120, 40));
        backButton.addActionListener(e -> {
            frame.dispose();
            new WelcomeUI(authController);
        });

        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void attemptRegistration() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String confirmPassword = new String(confirmPasswordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(frame, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success = authController.register(username, password);

        if (success) {
            JOptionPane.showMessageDialog(frame, "Registration Successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
            new LoginUI(authController);
        } else {
            JOptionPane.showMessageDialog(frame, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
