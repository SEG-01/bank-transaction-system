import javax.swing.*;
import java.util.ArrayList;

public class BankAccount implements Account {
    private double balance;
    private ArrayList<String> log; // Transaction log list

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        log = new ArrayList<>();
    }

    @Override
    public synchronized boolean withdraw(double amount) {
        String transaction; // stores transaction details
        
        if (balance >= amount) {
            balance -= amount;
            transaction = "Withdrawn: " + amount + " | Current Balance: " + balance;
            System.out.println(transaction);
            log.add(transaction); // Add to the transaction log
            return true;
        } else {
            // Ask user about overdraft
            int choice = JOptionPane.showOptionDialog(null, // Creates warning option select
                    "Insufficient funds. Accept overdraft?", 
                    "Overdraft Warning", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE, 
                    null, 
                    new String[]{"Accept Overdraft", "Deny Overdraft"}, 
                    "Deny Overdraft");

            if (choice == JOptionPane.YES_OPTION) {
                balance -= amount;  // Accepted, allow overdraft
                transaction = "Overdraft accepted. Withdrawn: " + amount + " | Current Balance: " + balance;
                System.out.println(transaction);
                log.add(transaction); // Add to the transaction log
                return true;
            } else {
                transaction = "Overdraft denied. Transaction canceled.";  // Overdraft denied, nothing happens
                System.out.println(transaction);
                log.add(transaction); // Add to the transaction log
                return false;
            }
        }
    }

    @Override
    public synchronized void deposit(double amount) {
        String transaction; //stores transaction to be added to log
        
        if (balance >= 10000) {
            System.out.println("Sorry, the deposit limit is 10,000");
            return;
        }

        balance += amount;
        transaction = "Deposited: " + amount + " | Current Balance: " + balance;
        System.out.println(transaction);
        log.add(transaction);
    }

    @Override
    public synchronized double getBalance() {
        return balance; // Return the instance's balance
    }
    
    // Method to get the transaction log
    public ArrayList<String> getLog() {
        return log;  // Return the transaction log
    }
}
