package bank_system.tests;

import static org.junit.jupiter.api.Assertions.*;
import bank_system.constants.CurrencyConstants;
import bank_system.controller.TransactionController;
import bank_system.model.BankAccount;
import bank_system.model.TransactionResult;
import bank_system.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * BankAccountTest class contains unit tests for the BankAccount class.
 * It tests the deposit, withdrawal, and transfer functionalities.
 */
class BankAccountTest {
    private User user1, user2;

    /**
     * Sets up the test environment before each test.
     * Initializes two User instances with initial balances.
     */
    @BeforeEach
    void setUp() {
        user1 = new User("Alice", "1234", new BankAccount(500));
        user2 = new User("Bob", "1234", new BankAccount(300));
    }

    /**
     * Tests successful deposit into the account.
     */
    @Test
    void testDeposit_Successful() {
        TransactionResult result = user1.account().deposit(200);
        assertTrue(result.isSuccess());
        assertEquals(700, user1.account().getBalance());
        assertEquals("Deposit Successful:" + CurrencyConstants.POUND + "200.0", result.getMessage());
    }

    /**
     * Tests deposit failure when the amount exceeds the deposit limit.
     */
    @Test
    void testDeposit_FailsWhenExceedingLimit() {
        TransactionResult result = user1.account().deposit(15000);
        assertFalse(result.isSuccess());
        assertEquals(500, user1.account().getBalance()); // Balance should remain unchanged
        assertEquals("Sorry, the deposit limit is" + CurrencyConstants.POUND + "10,000", result.getMessage());
    }

    /**
     * Tests successful withdrawal from the account.
     */
    @Test
    void testWithdraw_Successful() {
        TransactionResult result = user1.account().withdraw(100);
        assertTrue(result.isSuccess());
        assertEquals(400, user1.account().getBalance());
    }

    /**
     * Tests successful transfer into the account.
     */
    @Test
    void testTransferIn_Successful() {
        TransactionResult result = user1.account().transferIn(100, user2);
        assertTrue(result.isSuccess());
        assertEquals(600, user1.account().getBalance());
        assertEquals("Transferred In Successfully: 100.0", result.getMessage());
    }

    /**
     * Tests successful transfer out of the account.
     */
    @Test
    void testTransferOut_Successful() {
        TransactionResult result = user1.account().transferOut(200, user2);
        assertTrue(result.isSuccess());
        assertEquals(300, user1.account().getBalance());
        assertEquals("Transferred out Successful: 200.0", result.getMessage());
    }
}