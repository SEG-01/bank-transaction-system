package bank_system.view;

import javax.swing.*;
import java.awt.*;

public class WelcomeUI extends BaseUI{
    private JFrame frame;

    public WelcomeUI() {}
    
    @Override
    public void updateBalanceLabel() {}

    @Override
    public void initializeUI() {
        frame = new JFrame("Welcome to Bank System");
        frame.setSize(550, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Welcome!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        JButton loginButton = new JButton("Login");
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
