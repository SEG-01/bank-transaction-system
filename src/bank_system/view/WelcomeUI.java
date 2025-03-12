package bank_system.view;

import bank_system.controller.AuthController;

import javax.swing.*;
import java.awt.*;

public class WelcomeUI {
    private JFrame frame;
    private AuthController authController;

    public WelcomeUI(AuthController authController) {
        this.authController = authController;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Welcome to Bank System");
        frame.setSize(550, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Welcome!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        loginButton.addActionListener(e -> {
            frame.dispose();
            new LoginUI(authController);
        });

        registerButton.addActionListener(e -> {
            frame.dispose();
            new RegistrationUI(authController);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(titleLabel, gbc);

        gbc.gridy = 1;
        frame.add(loginButton, gbc);

        gbc.gridy = 2;
        frame.add(registerButton, gbc);

        frame.setVisible(true);
    }
}
