package bank_system.controller;
import javax.swing.*;

import bank_system.BankTransactionSystemGUI;
import bank_system.model.BankAccount;
import bank_system.model.TransactionResult;
import bank_system.view.BankUI;

import java.awt.*;

// Handles deposit and withdrawal actions
public class TransactionController {
    private BankAccount account;
    private BankUI ui;

    public TransactionController(BankAccount account, BankUI ui) {
        this.account = account;
        this.ui = ui;
    }

    public void handleDeposit(JTextField depositField) {
        try {
            double amount = validateAmount(depositField.getText());
            new Thread(() -> {
            	
            	TransactionResult result = account.deposit(amount);
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

    public void handleWithdrawal(JTextField withdrawField) {
        try {
            double amount = validateAmount(withdrawField.getText());
            new Thread(() -> {
            		
            	TransactionResult result = account.withdraw(amount);
            	
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
