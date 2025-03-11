public class BankAccount implements Account {
    private double balance; // Instance variable for each account

    public BankAccount(double initialBalance) {
        this.balance = initialBalance; // Initialize the balance for the instance
    }

    @Override
    public synchronized void deposit(double amount) {
        balance += amount; // Modify the instance's balance
        System.out.println("Deposited: " + amount + " | Current Balance: " + balance);
    }

    @Override
    public synchronized void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount; // Modify the instance's balance
            System.out.println("Withdrawn: " + amount + " | Current Balance: " + balance);
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    @Override
    public synchronized double getBalance() {
        return balance; // Return the instance's balance
    }
}
