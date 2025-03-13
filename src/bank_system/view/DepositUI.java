package bank_system.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import bank_system.controller.TransactionController;
import bank_system.model.User;

public class DepositUI {
    private JFrame frame;
    private JLabel balanceLabel;
    private JTextField recipientAccountField, transferAmountField;
    private TransactionController transactionController;
    private User user;
    
    public DepositUI(User user, TransactionController transaction_controller) {
        this.user = user;
        this.transactionController = transaction_controller;
        initializeUI();
    }
    
    private void initializeUI() {
        frame = new JFrame("Deposit");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Back Button
        JButton backButton = new JButton("←");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(backButton, gbc);
        
        // Balance Label
        balanceLabel = new JLabel("Balance: " + this.user.account().getBalance());
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(balanceLabel, gbc);
        
        // Recipient Account Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(new JLabel("Enter Deposit Amount: "), gbc);
        
        transferAmountField = new JTextField(20);
        gbc.gridy = 2;
        gbc.ipady = 20;
        frame.add(transferAmountField, gbc);
        
        
        // Confirm Transfer Button
        JButton depositButton = new JButton("Deposit");
        gbc.gridy = 5;
        gbc.ipady = 20;
        frame.add(depositButton, gbc);
        
        frame.setVisible(true);
        
        backButton.addActionListener(e -> {
            frame.dispose();
            new BankUI(this.user);
        });
        depositButton.addActionListener(e -> 
            transactionController.handleDeposit(this.user, transferAmountField)
        );
    }
}