package bank_system.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import bank_system.controller.TransactionController;
import bank_system.model.User;

public class DepositUI extends BaseUI {
    private JFrame frame;
    private JLabel balanceLabel;
    private JTextField recipientAccountField, transferAmountField;
    private TransactionController transaction_controller;
    private User user;
    
    public DepositUI(User user) {
        this.user = user;
    }
    
    @Override
    public void updateBalanceLabel() {
        SwingUtilities.invokeLater(() -> {
            double balance = this.user.account().getBalance();
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            String formattedBalance = "Balance: £" + formatter.format(balance);
            balanceLabel.setText(formattedBalance);
        });
    }

    @Override
    public void initializeUI() {
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
        balanceLabel = new JLabel();
        this.updateBalanceLabel();
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(balanceLabel, gbc);
        
        // Recipient Account Field
        gbc.insets = new Insets(10, 0, 0, 0);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(new JLabel("Enter Deposit Amount: "), gbc);
        
        transferAmountField = new JTextField(20);
        gbc.gridy = 2;
        gbc.ipady = 20;
        gbc.insets = new Insets(0, 0, 0, 0);
        frame.add(transferAmountField, gbc);
        
        
        // Confirm Transfer Button
        JButton depositButton = new JButton("Deposit");
        gbc.gridy = 5;
        gbc.ipady = 20;
        frame.add(depositButton, gbc);
        
        frame.setVisible(true);
        
        transaction_controller = new TransactionController(this.user.account(), this);

        backButton.addActionListener(e -> {
            frame.dispose();
            new BankUI(this.user).initializeUI();
        });
        depositButton.addActionListener(e -> 
            transaction_controller.handleDeposit(this.user, transferAmountField)
        );
    }
}