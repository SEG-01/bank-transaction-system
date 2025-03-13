package bank_system.view;

import bank_system.controller.TransactionController;
import bank_system.model.User;


import javax.swing.*;
import java.awt.*;

public class BankUI implements UI{
    private JFrame frame;
    private JLabel balanceLabel;
    private JLabel userIdLabel;
    private JTextField depositField, withdrawField;
    private TransactionController transaction_controller;

    private User user;

    public BankUI(User user) {
        this.user = user;
        
    }

    @Override
    public void initializeUI() {
        frame = new JFrame("Bank System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Welcome, " + this.user.getUsername(), JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        
        userIdLabel = new JLabel("Account Number: " + this.user.getId());
        userIdLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        
        balanceLabel = new JLabel("Balance: £" + this.user.account().getBalance());
        balanceLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));

        depositField = new JTextField(15);
        withdrawField = new JTextField(15);

        JButton depositButton = new JButton("Make a Deposit");
        JButton transferButton = new JButton("Transfer Money");
        JButton historyButton = new JButton("Transaction History");
        JButton withdrawButton = new JButton("Make a Withdraw");
        JButton logOutButton = new JButton("Log Out");

        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title Label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(titleLabel, gbc);
        
        // User id
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        frame.add(userIdLabel, gbc);
        
        // Balance Label
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.NORTH;
        frame.add(balanceLabel, gbc);
        
//        // Deposit Field and Button
//        gbc.gridx = 0;
//        gbc.gridy = 3;
//        gbc.gridwidth = 1;
//        gbc.anchor = GridBagConstraints.EAST;
//        frame.add(new JLabel("Deposit: "), gbc);

//        gbc.gridx = 1;
//        gbc.gridy = 3;
//        gbc.anchor = GridBagConstraints.WEST;
//        gbc.ipady = 20;
//        frame.add(depositField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(depositButton, gbc);

        // Withdraw Field and Button
//        gbc.gridx = 0;
//        gbc.gridy = 4;
//        gbc.ipady = 20;
//        gbc.anchor = GridBagConstraints.EAST;
//        frame.add(new JLabel("Withdraw: "), gbc);
//
//        gbc.gridx = 1;
//        gbc.anchor = GridBagConstraints.WEST;
//        frame.add(withdrawField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(withdrawButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(historyButton, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(transferButton, gbc);

        // LogOut Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(logOutButton, gbc);

        frame.setVisible(true);



        depositButton.addActionListener(e -> {
        	frame.dispose();
            new DepositUI(this.user).initializeUI();
    	});
        
        withdrawButton.addActionListener(e -> {
            transaction_controller.handleWithdrawal(this.user, withdrawField);
        });

        logOutButton.addActionListener(e -> {
            frame.dispose();
            new LoginUI().initializeUI();
        });

        transferButton.addActionListener(e -> {
            frame.dispose();
            new TransfersUI(this.user).initializeUI();
        });

        historyButton.addActionListener(e -> {
            frame.dispose();
            new TransactionsHistoryUI();
        });
    }

    public void updateBalanceLabel() {
        SwingUtilities.invokeLater(() -> balanceLabel.setText("Balance: £" + this.user.account().getBalance()));
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
