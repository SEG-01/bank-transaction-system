package bank_system.tests;

import bank_system.controller.TransactionController;
import bank_system.model.BankAccount;
import bank_system.model.TransactionResult;
import bank_system.model.User;
import bank_system.view.UI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

// These tests are not to be executed at once as they are independent
// an attempt to run the tests at once using the class will result in a failure
// with threads trying to access the same resources concurrently
// To run the tests, run the test individually (Nazarii)
public class TransactionControllerTest { 

    private UI ui;
    private User user;
    private BankAccount bankAccount;
    private JTextField recipientAccountField;
    private JTextField transferAmountField;
    private JTextField depositField;
    private JTextField withdrawField;
    private TransactionController transactionController;

    @BeforeEach
    public void setUp() {
        ui = new MockUI();
        bankAccount = new MockBankAccount();
        user = new MockUser(bankAccount);
        recipientAccountField = new JTextField();
        transferAmountField = new JTextField();
        depositField = new JTextField();
        withdrawField = new JTextField();
        transactionController = new TransactionController(ui);
    }

    @Test
    public void testHandleTransfer() throws ExecutionException, InterruptedException {
        recipientAccountField.setText("recipient");
        transferAmountField.setText("100");

        Future<String> result = transactionController.processTransaction(() -> {
            transactionController.handleTransfer(user, recipientAccountField, transferAmountField);
        });

        assertEquals("Transaction completed by pool-1-thread-1", result.get());
    }

    @Test
    public void testHandleDeposit() throws ExecutionException, InterruptedException {
        depositField.setText("100");

        Future<String> result = transactionController.processTransaction(() -> {
            transactionController.handleDeposit(user, depositField);
        });

        assertEquals("Transaction completed by pool-1-thread-1", result.get());
    }

    @Test
    public void testHandleWithdrawal() throws ExecutionException, InterruptedException {
        // Ensure the account has sufficient funds for the withdrawal
        bankAccount.deposit(200);
        withdrawField.setText("100");

        Future<Boolean> result = transactionController.handleWithdrawal(user, withdrawField);

        assertTrue(result.get());
    }

    // Mock classes for testing
    static class MockUI implements UI {
        @Override
        public void initializeUI() {}

        @Override
        public void showError(String message) {
            System.out.println("Error: " + message);
        }

        @Override
        public void showSuccess(String message) {
            System.out.println("Success: " + message);
        }

        @Override
        public void updateBalanceLabel() {
            System.out.println("Balance updated");
        }
    }

    static class MockUser extends User {
        private BankAccount account;

        public MockUser(BankAccount account) {
            super("username", "password", account);
            this.account = account;
        }

        @Override
        public BankAccount account() {
            return account;
        }
    }

    static class MockBankAccount extends BankAccount {
        private double balance;

        public MockBankAccount() {
            super(0);
        }

        @Override
        public TransactionResult deposit(double amount) {
            balance += amount;
            System.out.println("Deposited: " + amount + ", New Balance: " + balance);
            return new TransactionResult(true, "Deposit successful");
        }

        @Override
        public TransactionResult withdraw(double amount) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Withdrew: " + amount + ", New Balance: " + balance);
                return new TransactionResult(true, "Withdrawal successful");
            } else {
                System.out.println("Failed Withdrawal: " + amount + ", Balance: " + balance);
                return new TransactionResult(false, "Insufficient funds");
            }
        }

        @Override
        public TransactionResult transferIn(double amount, User sender) {
            balance += amount;
            return new TransactionResult(true, "Transfer in successful");
        }

        @Override
        public TransactionResult transferOut(double amount, User receiver) {
            if (balance >= amount) {
                balance -= amount;
                return new TransactionResult(true, "Transfer out successful");
            } else {
                return new TransactionResult(false, "Insufficient funds");
            }
        }

        @Override
        public double getBalance() {
            return balance;
        }
    }
}