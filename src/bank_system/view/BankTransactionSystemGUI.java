package bank_system.view;

import javax.swing.*;

import bank_system.controller.AuthController;

import java.awt.*;

public class BankTransactionSystemGUI {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public BankTransactionSystemGUI() {
        initializeUI();
    }

    private void initializeUI() {
        new WelcomeUI();
    }
}
