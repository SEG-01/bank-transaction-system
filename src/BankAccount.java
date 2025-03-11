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
	public synchronized TransactionResult withdraw(double amount) {
		String transaction; // stores transaction details

		if (balance >= amount) {
			balance -= amount;
			transaction = "Withdrawn: " + amount + " | Current Balance: " + balance;
			System.out.println(transaction);
			log.add(transaction); // Add to the transaction log
			return new TransactionResult(true, "Withdrawal Sucessful: £" + amount);
		} else {
			// Ask user about overdraft
			int choice = JOptionPane.showOptionDialog(null, // Creates warning option select
					"Insufficient funds. Accept overdraft?", "Overdraft Warning", JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE, null, new String[] { "Accept Overdraft", "Deny Overdraft" },
					"Deny Overdraft");

			if (choice == JOptionPane.YES_OPTION) {
				balance -= amount; // Accepted, allow overdraft
				transaction = "Overdraft accepted. Withdrawn: " + amount + " | Current Balance: " + balance;
				System.out.println(transaction);
				log.add(transaction); // Add to the transaction log
				return new TransactionResult(true, "Overdraft accepted.");
			} else {
				transaction = "Overdraft denied. Transaction canceled."; // Overdraft denied, nothing happens
				System.out.println(transaction);
				log.add(transaction); // Add to the transaction log
				return new TransactionResult(false, "Overdraft denied.");
			}
		}
	}

	@Override
	public synchronized TransactionResult deposit(double amount) {
		String transaction; // stores transaction to be added to log

		if (amount >= 10000) {
			System.out.println("Sorry, the deposit limit is 10,000");
			return new TransactionResult(false, "Sorry, the deposit limit is £10,000");
		}

		balance += amount;
		transaction = "Deposited: " + amount + " | Current Balance: " + balance;
		System.out.println(transaction);
		log.add(transaction);
		return new TransactionResult(true, "Deposit Sucessful: £" + amount);
	}

	@Override
	public synchronized double getBalance() {
		return balance; // Return the instance's balance
	}

	// Method to get the transaction log
	public ArrayList<String> getLog() {
		return log; // Return the transaction log
	}
}
