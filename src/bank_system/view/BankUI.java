package bank_system.view;

import bank_system.controller.TransactionController;
import bank_system.model.TransactionResult;
import bank_system.model.User;

import javax.swing.*;
import java.awt.*;

public class BankUI {
    private JFrame frame;
    private JLabel balanceLabel;
    private JTextField depositField, withdrawField;
    private TransactionController transaction_controller;

    private User user;

    public BankUI(User user) {
        this.user = user;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Bank System");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 3));

        balanceLabel = new JLabel("Balance: £" + user.account().getBalance());
        depositField = new JTextField();
        withdrawField = new JTextField();
        
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");

        frame.add(new JLabel(""));
        frame.add(balanceLabel);
        frame.add(depositField);
        frame.add(depositButton);
        frame.add(withdrawField);
        frame.add(withdrawButton);

        frame.setVisible(true);
        
        transaction_controller = new TransactionController(this.user.account(), this);
        depositButton.addActionListener(e -> transaction_controller.handleDeposit(depositField));
        withdrawButton.addActionListener(e -> transaction_controller.handleWithdrawal(withdrawField));
    }

    public void updateBalanceLabel() {
        SwingUtilities.invokeLater(() -> balanceLabel.setText("Balance: £" + user.account().getBalance()));
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

}
