<<<<<<< Updated upstream

public class BankAccount {
=======
public class BankAccount implements Account {
>>>>>>> Stashed changes
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    // Synchronized methods for thread-safe operations
    public synchronized void deposit(double amount) {
        balance += amount;
<<<<<<< Updated upstream
        System.out.println("Deposited: " + amount + " | Current Balance: " + balance);
    }

    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + " | Current Balance: " + balance);
        } else {
            System.out.println("Insufficient funds.");
=======
    }

    @Override
    public synchronized boolean withdraw(double amount) {
        if(amount > balance) {
        	return false;
>>>>>>> Stashed changes
        }
        balance -= amount;
        return true;
    }

    public synchronized double getBalance() {
        return balance;
    }
}
