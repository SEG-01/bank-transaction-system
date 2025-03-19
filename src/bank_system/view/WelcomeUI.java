package bank_system.view;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

import bank_system.controller.LocalizationManager;

import java.awt.*;

public class WelcomeUI extends BaseUI{
    private JFrame frame;
    private JComboBox<String> languageCombo;
    private JLabel titleLabel;
    private JButton loginButton;
    private JButton registerButton;

    public WelcomeUI() {}
    
    @Override
    public void updateBalanceLabel() {}

    @Override
    public void initializeUI() {

        languageCombo = new JComboBox<>(new String[]{"English", "Cymraeg"});

        frame = new JFrame("Welcome to Bank System");
        frame.setSize(550, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setLocationRelativeTo(null);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        titleLabel = new JLabel("Welcome!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        
        Dimension buttonSize = new Dimension(200, 50);
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);

         languageCombo.addActionListener(e -> {
            String selected = (String) languageCombo.getSelectedItem();
            if ("Cymraeg".equals(selected)) {
                LocalizationManager.setLocale(new Locale("cy", "GB"));
            } else {
                LocalizationManager.setLocale(new Locale("en", "US"));
            }
            // Refresh UI text after changing language
            refreshUI();
        });
        
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


        // Refresh all text from the resource bundle
    private void refreshUI() {
        
        
        ResourceBundle bundle = LocalizationManager.getBundle();
        frame.setTitle(bundle.getString("welcome.frameTitle"));
        titleLabel.setText(bundle.getString("welcome.title"));
        loginButton.setText(bundle.getString("button.login"));
        registerButton.setText(bundle.getString("button.register"));
        frame.revalidate();
        frame.repaint();
        }


}