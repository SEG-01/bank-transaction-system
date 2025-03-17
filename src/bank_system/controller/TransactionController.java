package bank_system.controller;
import javax.swing.*;

import bank_system.model.UserManager;
import bank_system.model.BankAccount;
import bank_system.model.TransactionResult;
import bank_system.model.User;
import bank_system.view.BankUI;
import bank_system.view.UI;

import java.awt.*;

// Handles deposit and withdrawal actions
public class TransactionController {
    private BankAccount account;
    private UI ui;
    
    public TransactionController(BankAccount account, UI ui) {
        this.account = account;
        this.ui = ui;
    }

    public void handleTransfer(User sender, JTextField recipientAccountField, JTextField transferAmountField) {
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
        	new Thread(() -> {
            	TransactionResult resultReceiver = receiver.account().transferIn(amount, sender);
                TransactionResult resultSender = sender.account().transferOut(amount, receiver);
                
                if(resultReceiver.isSuccess() && resultSender.isSuccess()) {
                	ui.updateBalanceLabel();
                	ui.showSuccess(resultReceiver.getMessage());
                }else {
                    ui.showError(resultReceiver.getMessage());
                }

            }).start();
            
        	recipientAccountField.setText("");
            transferAmountField.setText("");

        } catch (IllegalArgumentException ex) {
            ui.showError(ex.getMessage());
        }
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

    public boolean handleDeposit(User user, JTextField depositField) {
        try {
            double amount = validateAmount(depositField.getText());
            TransactionResult result = user.account().deposit(amount);

            if (result.isSuccess()) {
                ui.updateBalanceLabel();
                ui.showSuccess(result.getMessage());
                depositField.setText("");
                return true;  // Deposit successful
            } else {
                ui.showError(result.getMessage());
                return false; // Deposit failed
            }
        } catch (IllegalArgumentException ex) {
            ui.showError(ex.getMessage());
            return false; // Invalid input
        }
    }
    

    public boolean handleWithdrawal(User user, JTextField withdrawField) {
        try {
            double amount = validateAmount(withdrawField.getText());
            TransactionResult result = user.account().withdraw(amount);

            if (result.isSuccess()) {
                ui.updateBalanceLabel();
                ui.showSuccess(result.getMessage());
                withdrawField.setText("");
                return true;  // Withdrawal successful
            } else {
                ui.showError(result.getMessage());
                return false; // Withdrawal failed
            }
        } catch (IllegalArgumentException ex) {
            ui.showError(ex.getMessage());
            return false; // Invalid input
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
