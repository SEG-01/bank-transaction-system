package bank_system.view;

import javax.swing.*;

import bank_system.controller.TransactionController;
import bank_system.model.User;

public abstract class BaseUI implements UI {
    protected JFrame frame;
    protected JLabel balanceLabel;
    protected TransactionController transaction_controller;
    protected User user;

    public BaseUI() {}

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public abstract void initializeUI();

    public abstract void updateBalanceLabel();
}
