package bank_system.view;

import bank_system.controller.AuthController;
import bank_system.model.User;

import javax.swing.*;
import java.awt.*;

/**
 * LoginUI class represents the user interface for logging into the bank system.
 * It extends the BaseUI class and provides functionality for user authentication.
 */
public class LoginUI extends BaseUI {
    private JTextField usernameField; // Text field for entering the username
    private JPasswordField passwordField; // Password field for entering the password
    private AuthController authController; // Controller to handle authentication

    /**
     * Constructor to initialize the LoginUI.
     * Initializes the AuthController instance.
     */
    public LoginUI() {
        this.authController = AuthController.getInstance();
    }
    
    /**
     * Updates the balance label. This method is not used in LoginUI.
     */
    @Override
    public void updateBalanceLabel() {}

    /**
     * Initializes the user interface for logging in.
     * Sets up the frame and adds components such as labels, text fields, and buttons.
     */
    @Override
    public void initializeUI() {
        frame = new JFrame("Login - Bank System");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

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
        styleButton(loginButton, new Color(70, 130, 180), Color.BLACK);

        loginButton.addActionListener(e -> attemptLogin());

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
     * Attempts to log in the user with the provided username and password.
     * If successful, opens the BankUI. Otherwise, shows an error message.
     */
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
}