package bank_system;
public interface Account{
	double getBalance();
	TransactionResult deposit(double amount);
	TransactionResult withdraw(double amount);
}