package bank_system.model;

public interface Account{
	double getBalance();
	TransactionResult deposit(double amount);
	TransactionResult withdraw(double amount);
}