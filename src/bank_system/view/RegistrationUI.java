package bank_system.view;

import bank_system.controller.AuthController;

import javax.swing.*;
import java.awt.*;

/**
 * RegistrationUI class represents the user interface for registering a new user in the bank system.
 * It extends the BaseUI class and provides functionality for user registration.
 */
public class RegistrationUI extends BaseUI {
    private JTextField usernameField; // Text field for entering the username
    private JPasswordField passwordField; // Password field for entering the password
    private JPasswordField confirmPasswordField; // Password field for confirming the password
    private AuthController authController; // Controller to handle authentication

    /**
     * Constructor to initialize the RegistrationUI.
     * Initializes the AuthController instance.
     */
    public RegistrationUI() {
        this.authController = AuthController.getInstance();
    }
    
    /**
     * Updates the balance label. This method is not used in RegistrationUI.
     */
    @Override
    public void updateBalanceLabel() {}

    /**
     * Initializes the user interface for registering a new user.
     * Sets up the frame and adds components such as labels, text fields, and buttons.
     */
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
    
    /**
     * Styles a button with the given background and foreground colors.
     * 
     * @param button the button to be styled
     * @param backgroundColor the background color of the button
     * @param foregroundColor the foreground color of the button
     */
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
    
    /**
     * Attempts to register a new user with the provided username and password.
     * If successful, opens the LoginUI. Otherwise, shows an error message.
     */
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