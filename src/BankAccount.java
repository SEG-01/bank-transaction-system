public class BankAccount implements Account {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }

    @Override
    public synchronized boolean withdraw(double amount) {
        if(amount > balance) {
        	return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public synchronized double getBalance() {
        return balance; // Return the instance's balance
    }
}
