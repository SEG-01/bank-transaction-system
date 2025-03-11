import javax.swing.*;
import java.awt.*;

// Handles deposit and withdrawal actions
class TransactionHandler {
    private BankAccount account;
    private BankTransactionSystemGUI ui;

    public TransactionHandler(BankAccount account, BankTransactionSystemGUI ui) {
        this.account = account;
        this.ui = ui;
    }

    public void handleDeposit(JTextField depositField) {
        try {
            double amount = validateAmount(depositField.getText());
            new Thread(() -> {
                account.deposit(amount);
                ui.updateBalanceLabel();
                ui.showSuccess("Deposit successful: £" + amount);
                depositField.setText("");
            }).start();
        } catch (IllegalArgumentException ex) {
            ui.showError(ex.getMessage());
        }
    }

    public void handleWithdrawal(JTextField withdrawField) {
        try {
            double amount = validateAmount(withdrawField.getText());
            new Thread(() -> {
                if (account.withdraw(amount)) {
                    ui.updateBalanceLabel();
                    ui.showSuccess("Withdrawal successful: £" + amount);
                } else {
                    ui.showError("Insufficient funds.");
                }
                withdrawField.setText("");
            }).start();
        } catch (IllegalArgumentException ex) {
            ui.showError(ex.getMessage());
        }
    }

    private double validateAmount(String text) throws IllegalArgumentException {
        text = text.trim();
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Amount field cannot be empty.");
        }
        try {
            double amount = Double.parseDouble(text);
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero.");
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input. Please enter a valid numeric amount.");
        }
    }
}
