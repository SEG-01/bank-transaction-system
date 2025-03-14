package bank_system.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import bank_system.controller.TransactionController;
import bank_system.model.User;

public class TransfersUI implements UI{
    private JFrame frame;
    private JLabel balanceLabel;
    private JTextField recipientAccountField, transferAmountField;
    private TransactionController transaction_controller;
    private User user;
    
    public TransfersUI(User user) {
        this.user = user;
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

    @Override
    public void initializeUI() {
        frame = new JFrame("Transfers");
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
        balanceLabel = new JLabel("Balance: £" + this.user.account().getBalance());
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
        frame.add(new JLabel("Enter Recipient's Username:"), gbc);
        
        recipientAccountField = new JTextField(20);
        gbc.gridy = 2;
        gbc.ipady = 20;
        frame.add(recipientAccountField, gbc);
        
        // Transfer Amount Field
        gbc.gridy = 3;
        gbc.ipady = 20;
        frame.add(new JLabel("Enter Amount to Transfer:"), gbc);
        
        transferAmountField = new JTextField(20);
        gbc.gridy = 4;
        gbc.ipady = 20;
        frame.add(transferAmountField, gbc);
        
        // Confirm Transfer Button
        JButton confirmTransferButton = new JButton("Transfer");
        gbc.gridy = 5;
        gbc.ipady = 20;
        frame.add(confirmTransferButton, gbc);
        
        frame.setVisible(true);
        
        transaction_controller = new TransactionController(this.user.account(), this);

        backButton.addActionListener(e -> {
            frame.dispose();
            new BankUI(this.user).initializeUI();
        });
        confirmTransferButton.addActionListener(e -> 
            transaction_controller.handleTransfer(this.user, recipientAccountField, transferAmountField)
        );
    }
}