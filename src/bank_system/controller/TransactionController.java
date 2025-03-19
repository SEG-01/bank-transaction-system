package bank_system.controller;
import javax.swing.*;

import bank_system.model.UserManager;
import bank_system.model.TransactionResult;
import bank_system.model.User;
import bank_system.view.UI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


// Handles deposit and withdrawal actions
public class TransactionController {
    private UI ui;
    
    private final ExecutorService executor = Executors.newFixedThreadPool(4);
    
    public TransactionController(UI ui) {
        this.ui = ui;
    }

    public Future<String> processTransaction(Runnable transaction) {
        return executor.submit(() -> {
            transaction.run();
            return "Transaction completed by " + Thread.currentThread().getName();
        });
    }

    public void shutdown() {
        executor.shutdown();
    }

    public void handleTransfer(User sender, JTextField recipientAccountField, JTextField transferAmountField) {
        executor.submit(() -> {
            try {
                String recipientAccountUsername = validateRecipientAccount(recipientAccountField.getText());
                User receiver = UserManager.getUser(recipientAccountUsername);
                
                if(receiver == null){
                    ui.showError("Username is invalid.");
                    return;
                }
                
                double amount = validateAmount(transferAmountField.getText());
                
                if(receiver.getUsername() == sender.getUsername()){
                    ui.showError("Username is invalid.");
                    return;
                }
                
                TransactionResult resultReceiver = receiver.account().transferIn(amount, sender);
                TransactionResult resultSender = sender.account().transferOut(amount, receiver);
                
                if(resultReceiver.isSuccess() && resultSender.isSuccess()) {
                    ui.updateBalanceLabel();
                    ui.showSuccess(resultReceiver.getMessage());
                }else {
                    ui.showError(resultReceiver.getMessage());
                }

                recipientAccountField.setText("");
                transferAmountField.setText("");

            } catch (IllegalArgumentException ex) {
                ui.showError(ex.getMessage());
            }
        });
    }
    
    	
	private String validateRecipientAccount(String text) throws IllegalArgumentException {
        text = text.trim();
        if (text.isEmpty()) {
            throw new IllegalArgumentException("Recipient account field cannot be empty.");
        }
        return text;
    }
    	
    public void handlTransfersHistory() {
    	
    }

    public void handleDeposit(User user, JTextField depositField) {
        executor.submit(() -> {
            try {
                double amount = validateAmount(depositField.getText());
                TransactionResult result = user.account().deposit(amount);

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
    

    public Future<Boolean> handleWithdrawal(User user, JTextField withdrawField) {
        return executor.submit(() -> {
            try {
                double amount = validateAmount(withdrawField.getText());
                TransactionResult result = user.account().withdraw(amount);

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
