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
                ui.showError("User is not found.");
                return;
            }
        	
        	double amount = validateAmount(transferAmountField.getText());
        	
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

    public void handleDeposit(User user, JTextField depositField) {
        try {
            double amount = validateAmount(depositField.getText());
            new Thread(() -> {
            	TransactionResult result = user.account().deposit(amount);
                if(result.isSuccess()) {
                	ui.updateBalanceLabel();
                	ui.showSuccess(result.getMessage());
                }else {
                    ui.showError(result.getMessage());
                }
                depositField.setText("");
            }).start();
        } catch (IllegalArgumentException ex) {
            ui.showError(ex.getMessage());
        }
    }
    

    public void handleWithdrawal(User user, JTextField withdrawField) {
        try {
            double amount = validateAmount(withdrawField.getText());
            new Thread(() -> {
            	TransactionResult result = user.account().withdraw(amount);
                if (result.isSuccess()) {
                	ui.updateBalanceLabel();
                	ui.showSuccess(result.getMessage());
                } else {
                	ui.showError(result.getMessage());
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
