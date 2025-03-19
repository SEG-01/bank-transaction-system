package bank_system.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

import bank_system.constants.CurrencyConstants;
import bank_system.controller.TransactionController;
import bank_system.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WithdrawalUI extends BaseUI {
    private JFrame frame;
    private JLabel balanceLabel;
    private JTextField transferAmountField;
    private TransactionController transaction_controller;
    private User user;
    
    public WithdrawalUI(User user) {
        this.user = user;
    }

    @Override
    public void updateBalanceLabel() {
        SwingUtilities.invokeLater(() -> {
            double balance = this.user.account().getBalance();
            DecimalFormat formatter = new DecimalFormat("#,###.##");
            String formattedBalance = "Balance:" + CurrencyConstants.POUND + formatter.format(balance);
            balanceLabel.setText(formattedBalance);
        });
    }

    @Override
    public void initializeUI() {
        frame = new JFrame("Withdraw");
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
        
        // Withdrawal Amount Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.ipady = 20;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(new JLabel("Enter Withdrawal Amount: "), gbc);
        
        transferAmountField = new JTextField(20);
        gbc.gridy = 2;
        gbc.ipady = 20;
        frame.add(transferAmountField, gbc);
        
        // Withdraw Button
        JButton withdrawButton = new JButton("Withdraw");
        gbc.gridy = 5;
        gbc.ipady = 20;
        frame.add(withdrawButton, gbc);
        
        frame.setVisible(true);
        
        // Back Button Action
        backButton.addActionListener(e -> {
            frame.dispose();
            new BankUI(this.user).initializeUI();
        });

        // Withdraw Button Action
        withdrawButton.addActionListener(e -> withdrawalConfirmationBox());
    }

    private void withdrawalConfirmationBox() {
        // Get the withdrawal amount as a string
        String amountText = transferAmountField.getText().trim();

        // Validate if the field is empty
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter an amount.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Show confirmation dialog
        int response = JOptionPane.showConfirmDialog(
            frame,
            "Are you sure you want to withdraw" + CurrencyConstants.POUND + amountText + "?",
            "Confirm Withdrawal",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        // If the user clicks "Yes"
        if (response == JOptionPane.YES_OPTION) {
            transaction_controller = new TransactionController(this);

            Future<Boolean> future = transaction_controller.handleWithdrawal(user, transferAmountField);
            try {
                boolean success = future.get();
                SwingUtilities.invokeLater(() -> {
                    if (success) {
                        updateBalanceLabel();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Withdrawal failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, "Error processing withdrawal!", "Error", JOptionPane.ERROR_MESSAGE));
            }
        }
    }
}
