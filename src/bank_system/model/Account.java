package bank_system.model;

/**
 * Account interface represents a generic bank account.
 * It provides methods to get the balance, deposit money, and withdraw money.
 */
public interface Account {
    
    /**
     * Gets the current balance of the account.
     * 
     * @return the current balance
     */
    double getBalance();

    /**
     * Deposits a specified amount into the account.
     * 
     * @param amount the amount to be deposited
     * @return a TransactionResult indicating the success or failure of the deposit
     */
    TransactionResult deposit(double amount);

    /**
     * Withdraws a specified amount from the account.
     * 
     * @param amount the amount to be withdrawn
     * @return a TransactionResult indicating the success or failure of the withdrawal
     */
    TransactionResult withdraw(double amount);
}