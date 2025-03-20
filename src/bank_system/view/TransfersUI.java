package bank_system.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import bank_system.constants.CurrencyConstants;
import bank_system.controller.TransactionController;
import bank_system.model.User;

/**
 * TransfersUI class represents the user interface for transferring money to another account.
 * It extends the BaseUI class and provides functionality for making transfers.
 */
public class TransfersUI extends BaseUI {
    private JLabel balanceLabel; // Label to display the user's balance
    private JTextField recipientAccountField, transferAmountField; // Text fields for entering recipient's username and transfer amount
    
    /**
     * Constructor to initialize the TransfersUI with the given user.
     * 
     * @param user the user whose account will be used for the transfer
     */
    public TransfersUI(User user) {
        this.user = user;
    }
    
    /**
     * Updates the balance label with the current balance of the user's account.
     */
    @Override
    public void updateBalanceLabel() {
        SwingUtilities.invokeLater(() -> {
            double balance = this.user.account().getBalance();
            DecimalFormat formatter = new DecimalFormat("#,###.##");
            String formattedBalance = "Balance:" + CurrencyConstants.POUND + formatter.format(balance);
            balanceLabel.setText(formattedBalance);
        });
    }

    /**
     * Initializes the user interface for making a transfer.
     * Sets up the frame and adds components such as labels, text fields, and buttons.
     */
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
        
        // Initialize the TransactionController
        transaction_controller = new TransactionController(this);

        // Add action listeners to buttons
        backButton.addActionListener(e -> {
            frame.dispose();
            new BankUI(this.user).initializeUI();
        });
        confirmTransferButton.addActionListener(e -> 
            transaction_controller.handleTransfer(this.user, recipientAccountField, transferAmountField)
        );
    }
}