import javax.swing.*;
import java.util.ArrayList;

public class BankAccount {
    private double balance;
    private ArrayList<String> log; // Transaction log list
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
        log = new ArrayList<>();
    }

    // Synchronized methods for thread-safe operations
    public synchronized void deposit(double amount) {
    	String transaction; //stores transaction to be added to log
    	if (balance >= 10000) {
    		System.out.println("Sorry, the deposit limit is 10,000");
    		return;
    	}
    	
        balance += amount;
        transaction = ("Deposited: " + amount + " | Current Balance: " + balance);
        System.out.println(transaction);
        log.add(transaction);
    }

    public synchronized void withdraw(double amount) {
        String transaction; // stores transaction details
        
        if (balance >= amount) {
            balance -= amount;
            transaction = "Withdrawn: " + amount + " | Current Balance: " + balance;
            System.out.println(transaction);
            log.add(transaction); // Add to the transaction log
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
            } else {
                transaction = "Overdraft denied. Transaction canceled.";  // Overdraft denied, nothing happens
                System.out.println(transaction);
                log.add(transaction); // Add to the transaction log
            }
        }
    }


    public synchronized double getBalance() {
        return balance;
    }
    
 // Method to get the transaction log
    public ArrayList<String> getLog() {
        return log;  // Return the transaction log
    }
}