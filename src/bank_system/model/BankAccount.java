package bank_system.model;

import javax.swing.*;
import org.json.JSONObject;

import bank_system.constants.CurrencyConstants;

import org.json.JSONException;
import java.util.ArrayList;

/**
 * BankAccount class implements the Account interface and represents a bank account.
 * It provides methods to get the balance, deposit money, withdraw money, and handle transfers.
 */
public class BankAccount implements Account {
    private double balance; // The current balance of the account
    private ArrayList<JSONObject> log; // Transaction log list

    /**
     * Constructor to initialize the BankAccount with an initial balance.
     * 
     * @param initialBalance the initial balance of the account
     */
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        log = new ArrayList<>();
    }

    /**
     * Withdraws a specified amount from the account.
     * 
     * @param amount the amount to be withdrawn
     * @return a TransactionResult indicating the success or failure of the withdrawal
     */
    @Override
    public synchronized TransactionResult withdraw(double amount) {
        JSONObject transaction = new JSONObject();

        if (balance >= amount) {
            balance -= amount;    
            try {
                transaction.put("Type", "Withdraw");
                transaction.put("Amount", amount);
                transaction.put("Balance", balance);
                transaction.put("Time", System.currentTimeMillis());
            } catch (JSONException e) {
                e.printStackTrace();  // Log the exception (or handle it in a way you prefer)
                return new TransactionResult(false, "Failed to log the transaction");
            }
            System.out.println(transaction);
            log.add(transaction); // Add to the transaction log
            return new TransactionResult(true, "Withdrawal Successful:" + CurrencyConstants.POUND + amount);
        } else {
            // Ask user about overdraft
            int choice = JOptionPane.showOptionDialog(null, // Creates warning option select
                    "Insufficient funds. Accept overdraft?", "Overdraft Warning", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, new String[] { "Accept Overdraft", "Deny Overdraft" },
                    "Deny Overdraft");

            if (choice == JOptionPane.YES_OPTION) {
                balance -= amount; // Accepted, allow overdraft

                try {
                    transaction.put("Type", "Overdraft");
                    transaction.put("Amount", amount);
                    transaction.put("Balance", balance);
                    transaction.put("Time", System.currentTimeMillis());
                } catch (JSONException e) {
                    e.printStackTrace();  // Log the exception (or handle it in a way you prefer)
                    return new TransactionResult(false, "Failed to log the transaction");
                }
                System.out.println(transaction);
                log.add(transaction); // Add to the transaction log
                return new TransactionResult(true, "Overdraft accepted.");
            } else {
                return new TransactionResult(false, "Overdraft denied.");
            }
        }
    }

    /**
     * Deposits a specified amount into the account.
     * 
     * @param amount the amount to be deposited
     * @return a TransactionResult indicating the success or failure of the deposit
     */
    @Override
    public synchronized TransactionResult deposit(double amount) {
        if (amount >= 10000) {
            System.out.println("Sorry, the deposit limit is" + CurrencyConstants.POUND + "10,000");
            return new TransactionResult(false, "Sorry, the deposit limit is" + CurrencyConstants.POUND + "10,000");
        }

        balance += amount;
        JSONObject transaction = new JSONObject();
        try {
            transaction.put("Type", "Deposit");
            transaction.put("Amount", amount);
            transaction.put("Balance", balance);
            transaction.put("Time", System.currentTimeMillis());
        } catch (JSONException e) {
            e.printStackTrace();  // Log the exception (or handle it in a way you prefer)
            return new TransactionResult(false, "Failed to log the transaction");
        }
        System.out.println(transaction);
        log.add(transaction); // Add to the transaction log
        return new TransactionResult(true, "Deposit Successful:" + CurrencyConstants.POUND + amount);
    }

    /**
     * Transfers a specified amount into the account from another user.
     * 
     * @param amount the amount to be transferred in
     * @param sender the user sending the money
     * @return a TransactionResult indicating the success or failure of the transfer
     */
    public synchronized TransactionResult transferIn(double amount, User sender) {
        if (amount >= 10000) {
            System.out.println("Sorry, the deposit limit is 10,000");
            return new TransactionResult(false, "Sorry, the deposit limit is" + CurrencyConstants.POUND + " 10,000");
        }

        balance += amount;
        JSONObject transaction = new JSONObject();
        try {
            transaction.put("Type", "Transfer by " + sender.getUsername());
            transaction.put("Amount", amount);
            transaction.put("Balance", balance);
            transaction.put("Time", System.currentTimeMillis());
        } catch (JSONException e) {
            e.printStackTrace();  // Log the exception (or handle it in a way you prefer)
            return new TransactionResult(false, "Failed to log the transaction");
        }
  
        System.out.println(transaction);
        log.add(transaction);
        return new TransactionResult(true, "Transferred In Successfully: " + amount);
    }

    /**
     * Transfers a specified amount out of the account to another user.
     * 
     * @param amount the amount to be transferred out
     * @param sender the user receiving the money
     * @return a TransactionResult indicating the success or failure of the transfer
     */
    public synchronized TransactionResult transferOut(double amount, User sender) {
        if (amount >= 10000) {
            System.out.println("Sorry, the deposit limit is 10,000");
            return new TransactionResult(false, "Sorry, the deposit limit is" + CurrencyConstants.POUND + "10,000");
        }

        balance -= amount;
        JSONObject transaction = new JSONObject();
        try {
            transaction.put("Type", "Transfer to " + sender.getUsername());
            transaction.put("Amount", amount);
            transaction.put("Balance", balance);
            transaction.put("Time", System.currentTimeMillis());
        } catch (JSONException e) {
            e.printStackTrace();  // Log the exception (or handle it in a way you prefer)
            return new TransactionResult(false, "Failed to log the transaction");
        }
            
        System.out.println(transaction);
        log.add(transaction);
        return new TransactionResult(true, "Transferred out Successful: " + amount);
    }

    /**
     * Gets the current balance of the account.
     * 
     * @return the current balance
     */
    @Override
    public synchronized double getBalance() {
        return balance; // Return the instance's balance
    }

    /**
     * Gets the transaction log.
     * 
     * @return the transaction log
     */
    public ArrayList<JSONObject> getLog() {
        return log; // Return the transaction log
    }
}