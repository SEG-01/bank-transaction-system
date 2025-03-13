package bank_system.view;

import bank_system.controller.AuthController;

import javax.swing.*;
import java.awt.*;

public class RegistrationUI implements UI {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private AuthController authController;

    public RegistrationUI() {
    	this.authController = AuthController.getInstance();
        
    }
    
    public void updateBalanceLabel() {}

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void initializeUI() {
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
        styleButton(registerButton, new Color(70, 130, 180), Color.BLACK);

        // Back Button
        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(70, 130, 180), Color.BLACK);
        
        
        panel.add(registerButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
        
        backButton.addActionListener(e -> {
            frame.dispose();
            new WelcomeUI().initializeUI();
        });
        registerButton.addActionListener(e -> attemptRegistration());
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
            new LoginUI().initializeUI();   
        } else {
            JOptionPane.showMessageDialog(frame, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
