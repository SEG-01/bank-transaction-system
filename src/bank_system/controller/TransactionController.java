package bank_system.controller;

import javax.swing.*;

import bank_system.model.UserManager;
import bank_system.model.TransactionResult;
import bank_system.model.User;
import bank_system.view.UI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * TransactionController handles deposit, withdrawal, and transfer actions.
 * It uses an ExecutorService to manage concurrent transactions.
 */
public class TransactionController {
    private UI ui; // The UI component to interact with the user

    // ExecutorService to handle concurrent transactions
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * Constructor to initialize the TransactionController with a UI component.
     * 
     * @param ui the UI component
     */
    public TransactionController(UI ui) {
        this.ui = ui;
    }

    /**
     * Processes a transaction asynchronously.
     * 
     * @param transaction the transaction to be processed
     * @return a Future representing the result of the transaction
     */
    public Future<String> processTransaction(Runnable transaction) {
        return executor.submit(() -> {
            transaction.run();
            return "Transaction completed by " + Thread.currentThread().getName();
        });
    }

    /**
     * Shuts down the ExecutorService.
     */
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * Handles a transfer transaction from the sender to the recipient.
     * 
     * @param sender the user sending the money
     * @param recipientAccountField the text field containing the recipient's account username
     * @param transferAmountField the text field containing the transfer amount
     */
    public void handleTransfer(User sender, JTextField recipientAccountField, JTextField transferAmountField) {
        executor.submit(() -> {
            try {
                // Validate recipient account and amount
                String recipientAccountUsername = validateRecipientAccount(recipientAccountField.getText());
                User receiver = UserManager.getUser(recipientAccountUsername);

                if (receiver == null) {
                    
                if (receiver == null) {
                    ui.showError("Username is invalid.");
                    return;
                }
                    
                double amount = validateAmount(transferAmountField.getText());
                
                // Check for sufficient funds to avoid an overdraft
                if (sender.account().getBalance() < amount) {
                    ui.showError("Insufficient funds: Transfer amount exceeds available balance.");
                    return;
                }
                    
                // Use equals() instead of '==' to compare strings
                if (receiver.getUsername().equals(sender.getUsername())) {
                    ui.showError("Cannot transfer funds to your own account.");
                    return;
                }
                    
                TransactionResult resultReceiver = receiver.account().transferIn(amount, sender);
                TransactionResult resultSender = sender.account().transferOut(amount, receiver);
                    
                if (resultReceiver.isSuccess() && resultSender.isSuccess()) {
                    ui.updateBalanceLabel();
                    ui.showSuccess(resultReceiver.getMessage());
                } else {
                } else {
                    ui.showError(resultReceiver.getMessage());
                }
                    
                recipientAccountField.setText("");
                transferAmountField.setText("");
                    
            } catch (IllegalArgumentException ex) {
                ui.showError(ex.getMessage());
            }
        });
    }

    /**
     * Validates the recipient account username.
     * 
     * @param text the recipient account username
     * @return the trimmed username if valid
     * @throws IllegalArgumentException if the username is empty
     */
    private String validateRecipientAccount(String text) throws IllegalArgumentException {
        text = text.trim();
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Recipient account field cannot be empty.");
        }
        return text;
    }

    /**
     * Handles a deposit transaction for the user.
     * 
     * @param user the user making the deposit
     * @param depositField the text field containing the deposit amount
     */
    public void handleDeposit(User user, JTextField depositField) {
        executor.submit(() -> {
            try {
                // Validate the deposit amount
                double amount = validateAmount(depositField.getText());
                TransactionResult result = user.account().deposit(amount);

                // Update the UI based on the result
                if (result.isSuccess()) {
                    ui.updateBalanceLabel();
                    ui.showSuccess(result.getMessage());
                    depositField.setText("");
                } else {
                    ui.showError(result.getMessage());
                }
            } catch (IllegalArgumentException ex) {
                ui.showError(ex.getMessage());
            }
        });
    }

    /**
     * Handles a withdrawal transaction for the user.
     * 
     * @param user the user making the withdrawal
     * @param withdrawField the text field containing the withdrawal amount
     * @return a Future representing the result of the withdrawal
     */
    public Future<Boolean> handleWithdrawal(User user, JTextField withdrawField) {
        return executor.submit(() -> {
            try {
                // Validate the withdrawal amount
                double amount = validateAmount(withdrawField.getText());
                TransactionResult result = user.account().withdraw(amount);

                // Update the UI based on the result
                if (result.isSuccess()) {
                    SwingUtilities.invokeLater(() -> {
                        ui.updateBalanceLabel();
                        ui.showSuccess(result.getMessage());
                        withdrawField.setText("");
                    });
                    return true; // Withdrawal successful
                } else {
                    SwingUtilities.invokeLater(() -> ui.showError(result.getMessage()));
                    return false; // Withdrawal failed
                }
            } catch (IllegalArgumentException ex) {
                SwingUtilities.invokeLater(() -> ui.showError(ex.getMessage()));
                return false; // Invalid input
            }
        });
    }

    /**
     * Validates the amount for a transaction.
     * 
     * @param text the amount as a string
     * @return the parsed amount if valid
     * @throws IllegalArgumentException if the amount is invalid
     */
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