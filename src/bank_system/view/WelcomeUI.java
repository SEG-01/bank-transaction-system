package bank_system.view;

import javax.swing.*;
import java.awt.*;

/**
 * WelcomeUI class represents the welcome screen of the bank system.
 * It extends the BaseUI class and provides options for logging in or registering a new user.
 */
public class WelcomeUI extends BaseUI {
    
    /**
     * Constructor for WelcomeUI.
     */
    public WelcomeUI() {}

    /**
     * Updates the balance label. This method is not used in WelcomeUI.
     */
    @Override
    public void updateBalanceLabel() {}

    /**
     * Initializes the user interface for the welcome screen.
     * Sets up the frame and adds components such as labels and buttons.
     */
    @Override
    public void initializeUI() {
        frame = new JFrame("Welcome to Bank System");
        frame.setSize(550, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Login Button
        JButton loginButton = new JButton("Login");
        // Register Button
        JButton registerButton = new JButton("Register");
        
        Dimension buttonSize = new Dimension(200, 50);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(titleLabel, gbc);

        gbc.gridy = 1;
        frame.add(loginButton, gbc);

        gbc.gridy = 2;
        frame.add(registerButton, gbc);

        frame.setVisible(true);
        
        // Add action listeners to buttons
        loginButton.addActionListener(e -> {
            frame.dispose();
            new LoginUI().initializeUI();
        });

        registerButton.addActionListener(e -> {
            frame.dispose();
            new RegistrationUI().initializeUI();
        });
    }
}